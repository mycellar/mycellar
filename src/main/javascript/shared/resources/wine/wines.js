angular.module('mycellar.resources.wine.wines', ['ngResource']);

angular.module('mycellar.resources.wine.wines').factory('Wines', ['$resource', '$q', function ($resource, $q) {

  var Wines = $resource('/api/domain/wine/wines/:id', {id: '@id'}, {
    _like: {
      url: '/api/wine/wines/like',
      method: 'GET'
    },
    _count: {
      url: '/api/wine/wines',
      method: 'GET',
      params: {count: 0}
    }
  });

  Wines.count = function () {
    var deferred = $q.defer();
    Wines._count({}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
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
