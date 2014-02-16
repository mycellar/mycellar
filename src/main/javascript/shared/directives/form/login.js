angular.module('mycellar.directives.form.login', []);

angular.module('mycellar.directives.form.login').directive('loginForm', [
  'security', '$location',
  function(security, $location) {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/login-form.tpl.html',
      scope: {
        onLogin: '&',
        onResetPassword: '&',
        onCancel: '&'
      },
      link: function(scope, elem, attrs, ngModel) {
        scope.$watch(attrs.cancelButton, function(value) {
          scope.cancelButton = (value == undefined) ? true : value;
        });
      },
      controller: function($scope) {
        $scope.email = '';
        $scope.password = '';
        $scope.$watchCollection('[email, password]', function() {
          $scope.loginForm.email.$setValidity('badCredentials', true);
          $scope.loginForm.password.$setValidity('badCredentials', true);
        });
        $scope.login = function() {
          security.login($scope.email, $scope.password).then(function(response) {
            $scope.onLogin();
          }, function(response) {
            $scope.loginForm.email.$setValidity('badCredentials', false);
            $scope.loginForm.password.$setValidity('badCredentials', false);
          });
        };
        $scope.resetPassword = function() {
          $location.path('/reset-password-request');
          $scope.onResetPassword();
        };
        $scope.cancel = function() {
          $scope.onCancel();
        };
      }
    }
  }
]);
