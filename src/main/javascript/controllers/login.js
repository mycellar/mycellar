angular.module('mycellar.controllers.login', [
  'ngRoute',
  'mycellar.resources.user.users',
  'mycellar.services.login',
  'mycellar.services.security',
  'mycellar.services.error',
  'mycellar.directives.form.password'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/register', {
      templateUrl: 'partials/views/register.tpl.html',
      controller: 'RegisterController'
    });
  }
]);

angular.module('mycellar.controllers.login').controller('RegisterController', [
  '$scope', 'security', '$location', 'validityHelper',
  function ($scope, security, $location, validityHelper) {
    $scope.login = function() {
      $location.path('/');
    };
    $scope.logout = security.logout;
    $scope.register = function() {
      if ($scope.passwords.first !== '' && $scope.passwords.first === $scope.passwords.second) {
        $scope.user.password = $scope.passwords.first;
        return security.register($scope.user).error(function(data, status, headers, config) {
          if (data.errorKey != undefined) {
            angular.forEach(data.properties, function(property) {
              if ($scope.newUserForm[property] != undefined) {
                validityHelper.sinceChanged($scope.newUserForm[property], data.errorKey);
              }
            });
          }
        });
      }
    };
    $scope.user = {
      email: ''
    };
    $scope.passwords = {
        first: '',
        second: ''
    }
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
