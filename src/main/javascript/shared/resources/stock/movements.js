angular.module('mycellar.resources.stock.movements', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.stock.movements').factory('Movements', [
  '$resource', '$q',
  function($resource, $q) {
    var Movements = $resource('/api/stock/movements/:id', {id: '@id'}, {
      getAllForCellar: {
        url: '/api/stock/movements',
        method: 'GET'
      },
      validate: {
        url: '/api/stock/validateMovement',
        method: 'POST'
      }
    });

    Movements.count = function() {
      var deferred = $q.defer();
      Movements.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Movements;
  }
]);

angular.module('mycellar.resources.stock.movements').factory('AdminMovements', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/stock/movements',
      validateUrl: '/api/admin/domain/stock/validateMovement'
    });
  }
]);
