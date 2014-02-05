angular.module('mycellar.resources.user.users', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.user.users').factory('Users', [
  '$resource', '$q',
  function($resource, $q) {
    var Users = $resource('/api/user/users/:id', {id: '@id'}, {
      validate: {
        url: '/api/user/validateUser',
        method: 'POST'
      }
    });
  
    Users.count = function() {
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
    };
  
    return Users;
  }
]);

angular.module('mycellar.resources.user.users').factory('AdminUsers', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/user/users',
      validateUrl: '/api/admin/domain/booking/validateUser',
      likeUrl: '/api/admin/domain/user/users/like'
    });
  }
]);
