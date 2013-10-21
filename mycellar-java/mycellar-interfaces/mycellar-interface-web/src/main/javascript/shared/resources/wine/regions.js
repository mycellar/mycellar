angular.module('mycellar.resources.wine.regions', ['ngResource']);

angular.module('mycellar.resources.wine.regions').factory('Regions', ['$resource', '$q', function ($resource, $q) {

  var Regions = $resource('/api/domain/wine/regions');
  var Region = $resource('/api/domain/wine/region/:regionId');
  
  Regions.deleteById = function(id, fn) {
    return Region.delete({regionId: id}, fn);
  };
  
  Regions.count = function () {
    var deferred = $q.defer();
    Regions.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Regions.getById = function(id) {
    return Region.get({regionId: id});
  };
  
  Regions.new = function() {
    return new Region();
  };
  
  Regions.nameLike = function(name) {
    var deferred = $q.defer();
    Regions.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Regions;
}]);
