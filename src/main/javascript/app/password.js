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
                $location.path('/');
              }
              return value.data;
            });
          } else {
            $location.path('/');
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
    $scope.requestPasswordReset = function() {
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
    $scope.password = '';
    $scope.password2 = '';
    $scope.resetPassword = function() {
      security.resetPassword(key, $scope.password).success(function() {
        $location.path('/');
      });
    }
  }
]);
