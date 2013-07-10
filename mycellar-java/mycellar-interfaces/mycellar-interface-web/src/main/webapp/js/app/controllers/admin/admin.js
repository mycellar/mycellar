'use strict';

angular.module('mycellar').controller({
  AdminController: function ($scope, $resource, $http) {
    $scope.angularVersion = angular.version.full;
    $scope.mycellarVersion = mycellar.version.full;
    $http.get('/api/domain/stack/stacks?count=0').success(function(data, status, headers, config) {
      $scope.stackCount = data.count;
    });  
    $http.get('/api/domain/user/users?count=0').success(function(data, status, headers, config) {
      $scope.userCount = data.count;
    });
    $http.get('/api/domain/wine/wines?count=0').success(function(data, status, headers, config) {
      $scope.wineCount = data.count;
    });
  }
});