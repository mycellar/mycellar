angular.module('directives.login-bar', ['services.security']);

angular.module('directives.login-bar').directive('loginBar', [
  'security', 
  function(security) {
    return {
      restrict: 'E',
      replace: true,
      scope: true,
      templateUrl: 'partials/directives/login-bar.tpl.html',
      link: function($scope, $element, $attrs, $controller) {
        $scope.isLoggedIn = security.isAuthenticated;
        $scope.login = security.login;
        $scope.logout = security.logout;
        $scope.$watch(function() {
          return security.currentUser;
        }, function(currentUser) {
          $scope.currentUser = currentUser;
        });
        $scope.email = '';
        $scope.password = '';
      }
    }
  }
]);