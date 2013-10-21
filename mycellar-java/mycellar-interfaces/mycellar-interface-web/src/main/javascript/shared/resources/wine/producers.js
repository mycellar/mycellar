angular.module('mycellar.resources.wine.producers', ['ngResource']);

angular.module('mycellar.resources.wine.producers').factory('Producers', ['$resource', '$q', function ($resource, $q) {

  var Producers = $resource('/api/domain/wine/producers');
  var Producer = $resource('/api/domain/wine/producer/:producerId');
  
  Producers.deleteById = function(id, fn) {
    return Producer.delete({producerId: id}, fn);
  };
  
  Producers.count = function () {
    var deferred = $q.defer();
    Producers.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Producers.getById = function(id) {
    return Producer.get({producerId: id});
  };
  
  Producers.new = function() {
    return new Producer();
  };
  
  Producers.nameLike = function(name) {
    var deferred = $q.defer();
    Producers.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Producers;
}]);
