angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.resources.user.users',
  'mycellar.services.security',
  'mycellar.directives.form.password',
  'mycellar.directives.form.login'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/login', {
      templateUrl: 'partials/login.tpl.html',
      controller: 'LoginController'
    });
    $routeProvider.when('/register', {
      templateUrl: 'partials/login.tpl.html',
      controller: 'LoginController'
    });
  }
]);

angular.module('mycellar.controllers.login').controller('LoginController', [
  '$scope', 'security', 'Users', '$location',
  function ($scope, security, Users, $location) {
    $scope.email = '';
    $scope.password = '';
    $scope.login = function() {
      security.login($scope.email, $scope.password);
    };
    $scope.resetPasswordRequest = function() {
      $location.path('/reset-password-request');
    };
    $scope.logout = security.logout;
    $scope.register = security.register;
    $scope.user = new Users();
    $scope.user.password = '';
    $scope.password2 = '';
  }
]);
