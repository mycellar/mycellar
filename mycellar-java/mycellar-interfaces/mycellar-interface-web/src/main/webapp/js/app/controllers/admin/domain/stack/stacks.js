'use strict';

angular.module('mycellar').controller({
  AdminDomainStacksController: function ($scope, $resource, $http, $location) {
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/stack/stacks/list'),
      itemCountGet: $http.get('/api/domain/stack/stacks/count'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/stack/stack/' + itemId);
    };
  },
  AdminDomainStackController: function ($scope, $resource, $route, $location) {
    var stackId = $route.current.params.stackId;
    $scope.stackResource = $resource('/api/domain/stack/stack/:stackId');
    $scope.stack = $scope.stackResource.get({stackId: stackId});
    $scope.cancel = function () {
      $location.path('/admin/domain/stack/stacks/');
    };
  }
});