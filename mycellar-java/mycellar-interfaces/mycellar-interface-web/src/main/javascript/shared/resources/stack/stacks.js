angular.module('resources.stack.stacks', ['ngResource']);

angular.module('resources.stack.stacks').factory('Stacks', ['$resource', '$q', function ($resource, $q) {

  var Stacks = $resource('/api/domain/stack/stacks');
  var Stack = $resource('/api/domain/stack/stack/:stackId');
  
  Stack.deleteById = Stack.delete;
  Stack.delete = function(fn) {
    return Stack.deleteById({stackId: this.id}, fn);
  };
  Stacks.deleteById = function(id, fn) {
    return Stack.deleteById({stackId: id}, fn);
  };
  
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
  
  Stacks.getById = function(id) {
    return Stack.get({stackId: id});
  };
  
  return Stacks;
}]);
