angular.module('mycellar.directives.form.login', []);

angular.module('mycellar.directives.form.login').directive('loginForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/login-form.tpl.html',
      scope: {
        onLogin: '&',
        onResetPassword: '&',
        email: '=',
        password: '='
      },
      link: function(scope, elem, attrs, ngModel) {
        scope.$watch(attrs.buttons, function(value) {
          scope.buttons = (value == undefined) ? true : value;
        });
      },
      controller: function($scope) {
        $scope.login = function() {
          $scope.onLogin();
        };
        $scope.resetPassword = function() {
          $scope.onResetPassword();
        };
      }
    }
  }
]);
