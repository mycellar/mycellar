'use strict';

angular.module('mycellar').controller({
  AdminDomainStacksController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'count',
      ],
      ways: {
        count: 'desc',
      }
    };
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/stack/stacks/list'),
      itemCountGet: $http.get('/api/domain/stack/stacks/count'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/stack/stack/' + itemId);
    };
    $scope.sortBy = function(property) {
      if ($scope.sort.ways[property] == 'asc') {
        $scope.sort.ways[property] = 'desc';
      } else if ($scope.sort.ways[property] == 'desc') {
        $scope.sort.properties.splice($scope.sort.properties.indexOf(property), 1);
        $scope.sort.ways[property] = null;
      } else {
        $scope.sort.properties.push(property);
        $scope.sort.ways[property] = 'asc';
      }
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