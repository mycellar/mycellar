angular.module('mycellar.controllers.navigation', [
  'mycellar.services.security',
  'mycellar.services.menu'
]);

angular.module('mycellar.controllers.navigation').controller('NavigationController', [
  '$scope', 'menuService', 'security',
  function ($scope, menuService, security) {
    $scope.isCollapsed = true;
    $scope.menuService = menuService;
  
    $scope.isLoggedIn = security.isAuthenticated;
    $scope.login = security.login;
    $scope.logout = security.logout;
    $scope.email = '';
    $scope.password = '';
    $scope.$on('collapse', function (event) {
      $scope.isCollapsed = true;
      event.stopPropagation();
    });
  }
]);
