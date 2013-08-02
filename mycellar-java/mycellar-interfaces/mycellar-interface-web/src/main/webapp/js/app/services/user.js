'use strict';

function userServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (userEmail) {
        return $http.get("/api/domain/user/user?count=15&filters=email,"+userEmail+"&first=0&sort=email,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/user/users'),
        item: $resource('/api/domain/user/users/:userId')
      }
    };
  };
};

angular.module('mycellar')
  .provider('userService', userServiceProvider);
