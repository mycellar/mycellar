'use strict';

angular.module('mycellar').controller({
  AdminDomainUsersController: function ($scope, $location, userService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: userService.resource.list,
      defaultSort: ['lastname', 'firstname']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/user/user/' + itemId);
    };
    $scope.delete = function(itemId) {
      userService.resource.item.delete({userId: itemId}, function (value, headers) {
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
  AdminDomainUserController: function ($scope, $resource, $route, $location) {
    var userId = $route.current.params.userId;
    $scope.userResource = $resource('/api/domain/user/user/:userId');
    $scope.user = $scope.userResource.get({userId: userId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.user, $scope.backup);
      $scope.user.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/user/users/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.user);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.user);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/user/users/');
    };
  }
});