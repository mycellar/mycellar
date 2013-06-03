'use strict';

angular.module('mycellar').controller({
  AdminController: function ($scope, $resource, $http) {
    $scope.angularVersion = angular.version.full;
    $scope.mycellarVersion = mycellar.version.full;
    $scope.stackResource = $resource('/api/domain/stack/list/:first/:count');
    $http.get('/api/domain/stack/count').success(function(data, status, headers, config) {
      $scope.stackCount = data;
      $scope.firstStack = 0;
      $scope.lastStack = $scope.stackCount < 20 ? $scope.stackCount : 20;
      $scope.stacks = $scope.stackResource.query({first: $scope.firstStack, count: $scope.lastStack});  
    });
    $http.get('/api/domain/user/count').success(function(data, status, headers, config) {
      $scope.userCount = data;
    });
    $http.get('/api/domain/wine/count').success(function(data, status, headers, config) {
      $scope.wineCount = data;
    });
  }
});