angular.module('mycellar.resources.wine.appellations', ['ngResource']);

angular.module('mycellar.resources.wine.appellations').factory('Appellations', ['$resource', '$q', function ($resource, $q) {

  var Appellations = $resource('/api/domain/wine/appellations');
  var Appellation = $resource('/api/domain/wine/appellation/:appellationId');
  
  Appellations.deleteById = function(id, fn) {
    return Appellation.delete({appellationId: id}, fn);
  };
  
  Appellations.count = function () {
    var deferred = $q.defer();
    Appellations.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Appellations.getById = function(id) {
    return Appellation.get({appellationId: id});
  };
  
  Appellations.new = function() {
    return new Appellation();
  };
  
  Appellations.nameLike = function(name) {
    var deferred = $q.defer();
    Appellations.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Appellations;
}]);
