angular.module('mycellar.resources.wine.appellations', ['ngResource']);

angular.module('mycellar.resources.wine.appellations').factory('Appellations', ['$resource', '$q', function ($resource, $q) {

  var Appellations = $resource('/api/domain/wine/appellations/:id', {id: '@id'}, {
    validate: {
      url: '/api/domain/wine/validateAppellation',
      method: 'POST'
    }
  });

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
