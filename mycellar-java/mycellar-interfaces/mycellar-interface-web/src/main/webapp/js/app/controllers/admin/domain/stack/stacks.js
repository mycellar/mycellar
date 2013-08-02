'use strict';

angular.module('mycellar').controller({
  AdminDomainStacksController: function ($scope, $location, $route, stackService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: stackService.resource.list,
      defaultSort: ['count']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/stack/stack/' + itemId);
    };
    $scope.delete = function(itemId) {
      stackService.resource.item.delete({stackId: itemId}, function (value, headers) {
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
      stackService.resource.list.delete({}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
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