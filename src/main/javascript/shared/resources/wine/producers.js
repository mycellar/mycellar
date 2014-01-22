angular.module('mycellar.resources.wine.producers', ['ngResource']);

angular.module('mycellar.resources.wine.producers').factory('Producers', ['$resource', '$q', function ($resource, $q) {

  var Producers = $resource('/api/domain/wine/producers/:id', {id: '@id'}, {
    validate: {
      url: '/api/domain/wine/validateProducer',
      method: 'POST'
    }
  });

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
