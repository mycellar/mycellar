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
    $scope.login = function() {
      $location.path('/');
    };
    $scope.logout = security.logout;
    $scope.register = security.register;
    $scope.user = new Users();
    $scope.user.password = '';
    $scope.password2 = '';
  }
]);
