angular.module('mycellar.resources.stock.cellarShares', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.stock.cellarShares').factory('CellarShares', [
  '$resource', '$q',
  function($resource, $q) {
    var CellarShares = $resource('/api/stock/cellarShares/:id', {id: '@id'}, {
      getAllForCellar: {
        url: '/api/stock/cellarShares',
        method: 'GET'
      },
      validate: {
        url: '/api/stock/validateCellarShare',
        method: 'POST'
      }
    });

    CellarShares.count = function() {
      var deferred = $q.defer();
      CellarShares.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return CellarShares;
  }
]);

angular.module('mycellar.resources.stock.cellarShares').factory('AdminCellarShares', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/stock/cellarShares',
      validateUrl: '/api/admin/domain/stock/validateCellarShare'
    });
  }
]);
