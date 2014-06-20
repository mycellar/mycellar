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
      templateUrl: 'partials/views/login.tpl.html',
      controller: 'LoginController'
    });
    $routeProvider.when('/register', {
      templateUrl: 'partials/views/login.tpl.html',
      controller: 'LoginController'
    });
  }
]);

angular.module('mycellar.controllers.login').controller('LoginController', [
  '$scope', 'security', '$location',
  function ($scope, security, $location) {
    $scope.login = function() {
      $location.path('/');
    };
    $scope.logout = security.logout;
    $scope.register = security.register;
    $scope.user = {
      email: '',
      password: ''
    };
    $scope.password2 = '';
  }
]);
