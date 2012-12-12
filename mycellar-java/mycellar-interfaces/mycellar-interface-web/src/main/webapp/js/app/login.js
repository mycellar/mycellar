'use strict';

angular.module('mycellar').controller({
  LoginController: function ($scope, $http, authService) {
    $scope.login = function() {
      $http.post('auth/login').success(function() {
        authService.loginConfirmed();
        $scope.isLoggedIn = true;
      });
    };
    $scope.logout = function() {
      $http.post('auth/logout').success(function() {
        $scope.isLoggedIn = true;
      });
    }
    $scope.isLoggedIn = false;
  }
});