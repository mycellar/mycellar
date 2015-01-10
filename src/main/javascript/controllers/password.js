angular.module('mycellar.controllers.password', [
  'ngRoute',
  'mycellar.services.security'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/reset-password-request', {
      templateUrl: 'partials/views/reset-password-request.tpl.html',
      controller: 'ResetPasswordRequestController'
    });
    
    $routeProvider.when('/reset-password', {
      templateUrl: 'partials/views/reset-password.tpl.html',
      controller: 'ResetPasswordController',
      resolve: {
        key: ['$route', function ($route) {
          return $route.current.params.key;
        }],
        email: ['$route', 'security', '$location', function ($route, security, $location) {
          var key = $route.current.params.key;
          if (key != null && key != '') {
            return security.getMailFromRequestKey(key).then(function(value) {
              if (value == null || value.status != '200') {
                $location.url('/');
              }
              return value.data;
            });
          } else {
            $location.url('/');
            return null;
          }
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.password').controller('ResetPasswordRequestController', [
  '$scope', 'security',
  function ($scope, security) {
    $scope.email = '';
    $scope.disabled = false;
    $scope.requestPasswordReset = function() {
      $scope.disabled = true;
      security.sendPasswordResetMail($scope.email).success(function() {
        $scope.mailSent = true;
      });
    }
    $scope.mailSent = false;
  }
]);

angular.module('mycellar.controllers.password').controller('ResetPasswordController', [
  '$scope', 'security', 'key', 'email', '$location',
  function ($scope, security, key, email, $location) {
    $scope.email = email;
    $scope.passwords = {
      password: '',
      password2: ''
    };
    $scope.resetPassword = function() {
      if  ($scope.passwords.password !== '' && $scope.passwords.password === $scope.passwords.password2) {
        security.resetPassword(key, $scope.passwords.password).success(function() {
          security.login($scope.email, $scope.passwords.password);
        });
      }
    }
  }
]);
