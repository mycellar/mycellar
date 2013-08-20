angular.module('mycellar').controller('NavigationController', [
  '$scope', 'menuService', 'security', // 'notifications', 'httpRequestTracker',
  function ($scope, menuService, security // , notifications, httpRequestTracker
      ) {
    $scope.isCollapsed = true;
    $scope.menuService = menuService;
    $scope.logout = security.logout;
  
//    $scope.isAuthenticated = security.isAuthenticated;
//    $scope.isAdmin = security.isAdmin;
  
//    $scope.hasPendingRequests = function () {
//      return httpRequestTracker.hasPendingRequests();
//    };
  }
]);
