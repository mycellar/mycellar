angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.resources.user.users',
  'mycellar.services.security',
  'mycellar.services.validation',
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
  '$scope', 'security', '$location', 'validityHelper',
  function ($scope, security, $location, validityHelper) {
    $scope.login = function() {
      $location.path('/');
    };
    $scope.logout = security.logout;
    $scope.register = function(user) {
      return security.register(user).error(function(data, status, headers, config) {
        if (data.errorKey != undefined) {
          angular.forEach(data.properties, function(property) {
            if ($scope.newUserForm[property] != undefined) {
              validityHelper.sinceChanged($scope.newUserForm[property], data.errorKey);
            }
          });
        }
      });
    };
    $scope.user = {
      email: '',
      password: ''
    };
    $scope.password2 = '';
  }
]);
