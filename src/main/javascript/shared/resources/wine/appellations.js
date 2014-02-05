angular.module('mycellar.resources.wine.appellations', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.wine.appellations').factory('Appellations', [
  '$resource', '$q',
  function($resource, $q) {
    var Appellations = $resource('/api/wine/appellations/:id', {id: '@id'}, {
      validate: {
        url: '/api/wine/validateAppellation',
        method: 'POST'
      }
    });

    Appellations.count = function() {
      var deferred = $q.defer();
      Appellations.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Appellations.nameLike = function(name) {
      var deferred = $q.defer();
      Appellations.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Appellations;
  }
]);

angular.module('mycellar.resources.wine.appellations').factory('AdminAppellations', [
  'adminDomainResource',
  function(adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/wine/appellations',
      validateUrl: '/api/admin/domain/wine/validateAppellation',
      likeUrl: '/api/admin/domain/wine/appellations/like'
    });
  }
]);
