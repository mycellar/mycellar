angular.module('mycellar.resources.wine.wines', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.wine.wines').factory('Wines', [
  '$resource', '$q',
  function($resource, $q) {
    var Wines = $resource('/api/wine/wines/:id', {id: '@id'}, {
      _like: {
        url: '/api/wine/wines/like',
        method: 'GET'
      },
      validate: {
        url: '/api/wine/validateWine',
        method: 'POST'
      }
    });

    Wines.count = function() {
      var deferred = $q.defer();
      Wines.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Wines.like = function(input) {
      var deferred = $q.defer();
      Wines._like({
        input: input,
        first: 0,
        count: 20
      }, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Wines;
  }
]);

angular.module('mycellar.resources.wine.wines').factory('AdminWines', [
  'adminDomainResource',
  function(adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/wine/wines',
      validateUrl: '/api/admin/domain/wine/validateWine',
      likeUrl: '/api/admin/domain/wine/wines/like'
    });
  }
]);
