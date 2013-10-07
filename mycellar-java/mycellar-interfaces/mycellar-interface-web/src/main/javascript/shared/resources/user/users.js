angular.module('resources.user.users', ['ngResource']);

angular.module('resources.user.users').factory('Users', ['$resource', '$q', function ($resource, $q) {

  var Users = $resource('/api/domain/user/users');
  var User = $resource('/api/domain/user/user/:userId');
  
  User.deleteById = User.delete;
  User.delete = function(fn) {
    return User.deleteById({userId: this.id}, fn);
  };
  Users.deleteById = function(id, fn) {
    return User.deleteById({userId: id}, fn);
  };
  
  Users.count = function () {
    var deferred = $q.defer();
    Users.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Users.like = function(value) {
    var deferred = $q.defer();
    Users.get({first: 0, count: 15, filters: 'lastname,'+value, sort: 'lastname,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  }
  
  Users.getById = function(id) {
    return User.get({userId: id});
  };
  
  Users.new = function() {
    return new User();
  };

  return Users;
}]);
