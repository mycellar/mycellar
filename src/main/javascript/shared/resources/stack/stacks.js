angular.module('mycellar.resources.stack.stacks', ['ngResource']);

angular.module('mycellar.resources.stack.stacks').factory('Stacks', ['$resource', '$q', function ($resource, $q) {

  var Stacks = $resource('/api/domain/stack/stacks/:id', {id: '@id'}, {});
  var Stack = $resource('/api/domain/stack/stack/:stackId');

  Stacks.count = function () {
    var deferred = $q.defer();
    Stacks.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  return Stacks;
}]);
