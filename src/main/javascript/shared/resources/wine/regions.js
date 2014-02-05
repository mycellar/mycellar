angular.module('mycellar.resources.wine.regions', [
  'ngResource',
  'mycellar.services.admin.resource',
]);

angular.module('mycellar.resources.wine.regions').factory('Regions', [
  '$resource', '$q',
  function($resource, $q) {
    var Regions = $resource('/api/wine/regions/:id', {id: '@id'}, {
      validate: {
        url: '/api/wine/validateRegion',
        method: 'POST'
      }
    });

    Regions.count = function() {
      var deferred = $q.defer();
      Regions.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Regions.nameLike = function(name) {
      var deferred = $q.defer();
      Regions.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Regions;
  }
]);

angular.module('mycellar.resources.wine.regions').factory('AdminRegions', [
  'adminDomainResource',
  function(adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/wine/regions',
      validateUrl: '/api/admin/domain/wine/validateRegion',
      likeUrl: '/api/admin/domain/wine/regions/like'
    });
  }
]);
