angular.module('mycellar.resources.stock.cellarShares', ['ngResource']);

angular.module('mycellar.resources.stock.cellarShares').factory('CellarShares', ['$resource', '$q', function ($resource, $q) {

  var CellarShares = $resource('/api/domain/stock/cellarShares', {}, {
    getAllForCellar: {
      url: '/api/stock/cellarShares',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateCellarShare',
      method: 'POST'
    }
  });
  var CellarShare = $resource('/api/domain/stock/cellarShare/:cellarShareId');

  CellarShares.deleteById = function(id, fn) {
    return CellarShare.delete({cellarShareId: id}, fn);
  };

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

  CellarShares.getById = function(id) {
    return CellarShare.get({cellarShareId: id});
  };

  CellarShares.new = function() {
    return new CellarShare();
  };

  return CellarShares;
}]);
