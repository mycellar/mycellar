angular.module('mycellar.resources.stock.cellars', ['ngResource']);

angular.module('mycellar.resources.stock.cellars').factory('Cellars', ['$resource', '$q', function ($resource, $q) {

  var Cellars = $resource('/api/domain/stock/cellars/:id', {id: '@id'}, {
    getAllForCurrentUser: {
      url: '/api/stock/cellars',
      method: 'GET'
    },
    validate: {
      url: '/api/domain/stock/validateCellar',
      method: 'POST'
    }
  });

  Cellars.count = function () {
    var deferred = $q.defer();
    Cellars.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  Cellars.nameLike = function(name) {
    var deferred = $q.defer();
    Cellars.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Cellars;
}]);
