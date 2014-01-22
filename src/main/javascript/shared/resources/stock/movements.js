angular.module('mycellar.resources.stock.movements', ['ngResource']);

angular.module('mycellar.resources.stock.movements').factory('Movements', ['$resource', '$q', function ($resource, $q) {

  var Movements = $resource('/api/domain/stock/movements/:id', {id: '@id'}, {
    getAllForCellar: {
      url: '/api/stock/movements',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateMovement',
      method: 'POST'
    }
  });

  Movements.count = function () {
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
}]);
