'use strict';

angular.module('mycellar').controller({
  AdminDomainUsersController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'lastname',
        'firstname'
      ],
      ways: {
        lastname: 'asc',
        firstname: 'asc',
        email: null
      }
    };
    $scope.filters = {
      lastname: '',
      firstname: '',
      email: ''
    }
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/user/users')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/user/user/' + itemId);
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