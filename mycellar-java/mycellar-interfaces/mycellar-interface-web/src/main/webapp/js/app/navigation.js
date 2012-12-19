'use strict';

angular.module('mycellar').controller({
  NavigationController: function ($scope, $http) {
    $scope.getMenu = function() {
      $http.get('navigation/menu').success(function() {
        
      });
    };
    $scope.menuNotLoggedIn = false;
  }
});