angular.module('mycellar.resources.stock.stocks', ['ngResource']);

angular.module('mycellar.resources.stock.stocks').factory('Stocks', ['$resource', '$q', function ($resource, $q) {

  var Stocks = $resource('/api/domain/stock/stocks', {}, {
    getAllForCellar: {
      url: '/api/stock/stocks',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateStock',
      method: 'POST'
    }
  });
  var Stock = $resource('/api/domain/stock/stock/:stockId');

  Stocks.deleteById = function(id, fn) {
    return Stock.delete({stockId: id}, fn);
  };

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

  Stocks.getById = function(id) {
    return Stock.get({stockId: id});
  };

  Stocks.new = function() {
    return new Stock();
  };

  return Stocks;
}]);
