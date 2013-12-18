angular.module('mycellar.resources.wine.wines', ['ngResource']);

angular.module('mycellar.resources.wine.wines').factory('Wines', ['$resource', '$q', function ($resource, $q) {

  var Wines = $resource('/api/wine/wines', {}, {
    _like: {
      url: '/api/wine/wines/like',
      method: 'GET'
    }
  });
  var Wine = $resource('/api/domain/wine/wine/:wineId');
  
  Wines.deleteById = function(id, fn) {
    return Wine.delete({wineId: id}, fn);
  };
  
  Wines.count = function () {
    var deferred = $q.defer();
    Wines.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Wines.getById = function(id) {
    return Wine.get({wineId: id});
  };
  
  Wines.new = function() {
    return new Wine();
  };

  Wines.like = function(input) {
    var deferred = $q.defer();
    Wines._like({
      input: input,
      first: 0,
      count: 20
    }, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Wines;
}]);
