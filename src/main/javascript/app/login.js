angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.resources.user.users',
  'mycellar.services.security.service',
  'mycellar.directives.form.password'
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
  '$scope', 'security', 'Users',
  function ($scope, security, Users) {
    $scope.email = '';
    $scope.password = '';
    $scope.login = security.login;
    $scope.logout = security.logout;
    $scope.register = security.register;
    $scope.user = new Users();
    $scope.user.password = '';
    $scope.password2 = '';
  }
]);
