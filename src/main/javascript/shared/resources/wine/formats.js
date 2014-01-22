angular.module('mycellar.resources.wine.formats', ['ngResource']);

angular.module('mycellar.resources.wine.formats').factory('Formats', ['$resource', '$q', function ($resource, $q) {

  var Formats = $resource('/api/domain/wine/formats/:id', {id: '@id'}, {});

  Formats.count = function () {
    var deferred = $q.defer();
    Formats.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  Formats.nameLike = function(name) {
    var deferred = $q.defer();
    Formats.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Formats;
}]);
