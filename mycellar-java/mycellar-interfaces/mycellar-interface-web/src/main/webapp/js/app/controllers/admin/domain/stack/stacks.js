'use strict';

angular.module('mycellar').controller({
  AdminDomainStacksController: function ($scope, $resource, $http) {
    $scope.stackResource = $resource('/api/domain/stack/stacks/list');
    $http.get('/api/domain/stack/stacks/count').success(function(data, status, headers, config) {
      $scope.stackCount = data;
      $scope.firstStack = 0;
      $scope.lastStack = $scope.stackCount < 20 ? $scope.stackCount : 20;
      $scope.stacks = $scope.stackResource.query({first: $scope.firstStack, count: $scope.lastStack});  
    });
  }
});