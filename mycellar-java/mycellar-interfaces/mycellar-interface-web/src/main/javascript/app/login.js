angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.services.security.service'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/login', {
      templateUrl: 'partials/login.tpl.html',
      controller: 'LoginController'
    });
  }
]);

angular.module('mycellar.controllers.login').controller('LoginController', [
  '$scope', 'security',
  function ($scope, security) {
    $scope.email = '';
    $scope.password = '';
    $scope.login = security.login;
    $scope.logout = security.logout;
    $scope.register = security.register;
  }
]);
