angular.module('mycellar.resources.stock.stocks', ['ngResource']);

angular.module('mycellar.resources.stock.stocks').factory('Stocks', ['$resource', '$q', function ($resource, $q) {

  var Stocks = $resource('/api/domain/stock/stocks/:id', {id: '@id'}, {
    getAllForCellar: {
      url: '/api/stock/stocks',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateStock',
      method: 'POST'
    },
    arrival: {
      url: '/api/stock/arrival',
      method: 'POST'
    },
    drink: {
      url: '/api/stock/drink',
      method: 'POST'
    }
  });

  Stocks.count = function () {
    var deferred = $q.defer();
    Stocks.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Stocks;
}]);
