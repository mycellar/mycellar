angular.module('mycellar.resources.wine.countries', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.wine.countries').factory('Countries', [
  '$resource', '$q',
  function($resource, $q) {
    var Countries = $resource('/api/wine/countries/:id', {id: '@id'}, {
      validate: {
        url: '/api/wine/validateCountry',
        method: 'POST'
      }
    });

    Countries.count = function() {
      var deferred = $q.defer();
      Countries.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Countries.nameLike = function(name) {
      var deferred = $q.defer();
      Countries.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Countries;
  }
]);

angular.module('mycellar.resources.wine.countries').factory('AdminCountries', [
  'adminDomainResource',
  function(adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/wine/countries',
      validateUrl: '/api/admin/domain/wine/validateCountry',
      likeUrl: '/api/admin/domain/wine/countries/like'
    });
  }
]);
