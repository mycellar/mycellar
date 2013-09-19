angular.module('mycellar').controller('NavigationController', [
  '$scope', 'menuService', 'security',
  function ($scope, menuService, security) {
    $scope.isCollapsed = true;
    $scope.menuService = menuService;
  
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
    $scope.$on('collapse', function (event) {
      $scope.isCollapsed = true;
      event.stopPropagation();
    });
  }
]);
