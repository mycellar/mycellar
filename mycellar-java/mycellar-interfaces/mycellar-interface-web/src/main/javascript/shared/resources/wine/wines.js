angular.module('resources.wine.wines', ['ngResource']);

angular.module('resources.wine.wines').factory('Wines', ['$resource', '$q', function ($resource, $q) {

  var Wines = $resource('/api/domain/wine/wines');
  var Wine = $resource('/api/domain/wine/wine/:wineId');
  
  Wine.deleteById = Wine.delete;
  Wine.delete = function(fn) {
    return Wine.deleteById({wineId: this.id}, fn);
  };
  Wines.deleteById = function(id, fn) {
    return Wine.deleteById({wineId: id}, fn);
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

  return Wines;
}]);
