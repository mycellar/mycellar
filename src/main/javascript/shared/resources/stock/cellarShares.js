angular.module('mycellar.resources.stock.cellarShares', ['ngResource']);

angular.module('mycellar.resources.stock.cellarShares').factory('CellarShares', ['$resource', '$q', function ($resource, $q) {

  var CellarShares = $resource('/api/domain/stock/cellarShares/:id', {id: '@id'}, {
    getAllForCellar: {
      url: '/api/stock/cellarShares',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateCellarShare',
      method: 'POST'
    }
  });

  CellarShares.count = function () {
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
}]);
