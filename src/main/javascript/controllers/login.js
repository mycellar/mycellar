angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.resources.user.users',
  'mycellar.services.login',
  'mycellar.services.security',
  'mycellar.services.validation',
  'mycellar.directives.form.password'
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

angular.module('mycellar.controllers.login').controller('LoginDialogController', [
  '$scope', 'security', '$location', 'loginDialogService',
  function ($scope, security, $location, loginDialogService) {
    $scope.login = function() {
      security.login($scope.email, $scope.password).then(function(response) {
        loginDialogService.loginSuccess();
      }, function(response) {
        loginDialogService.loginFailure();
      });
    };
    $scope.cancel = function() {
      loginDialogService.cancel();
    };
    $scope.forgotPassword = function() {
      loginDialogService.forgotPassword();
    };
  }
]);
