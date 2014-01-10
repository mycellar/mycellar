angular.module('mycellar.resources.stock.movements', ['ngResource']);

angular.module('mycellar.resources.stock.movements').factory('Movements', ['$resource', '$q', function ($resource, $q) {

  var Movements = $resource('/api/domain/stock/movements', {}, {
    getAllForCellar: {
      url: '/api/stock/movements',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateMovement',
      method: 'POST'
    }
  });
  var Movement = $resource('/api/domain/stock/movement/:movementId');

  Movements.deleteById = function(id, fn) {
    return Movement.delete({movementId: id}, fn);
  };

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

  Movements.getById = function(id) {
    return Movement.get({movementId: id});
  };

  Movements.new = function() {
    return new Movement();
  };

  return Movements;
}]);
