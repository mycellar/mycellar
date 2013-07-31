'use strict';

angular.module('mycellar').controller({
  AdminDomainStacksController: function ($scope, $resource, $http, $location, $route) {
    $scope.sort = {
      properties: [
        'count',
      ],
      ways: {
        count: 'desc',
      }
    };
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/stack/stacks'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/stack/stack/' + itemId);
    };
    $scope.delete = function(itemId) {
      $resource('/api/domain/stack/stack/:stackId').delete({stackId: itemId}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
    };
    $scope.deleteAll = function() {
      $resource('/api/domain/stack/stacks/').delete({}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
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
    $scope.clearFilters = function() {
      for (var filter in $scope.filters) {
        $scope.filters[filter] = '';
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