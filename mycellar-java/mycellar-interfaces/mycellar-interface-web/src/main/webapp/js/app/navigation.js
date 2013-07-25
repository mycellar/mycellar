'use strict';

angular.module('mycellar').directive('mycellarNav', function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/nav.html',
  }
});

angular.module('mycellar').controller({
  NavigationController: function ($scope, $resource, $rootScope, $location) {
    $rootScope.$on('$routeChangeSuccess', function() {
      var path = $location.path();
      if (path.match("/admin/domain")) {
        path = "/admin/lists";
      }
      angular.forEach($scope.menus, function(item) {
        if (item.route == undefined) {
          var found = false;
          angular.forEach(item.pages, function(page) {
            if (page.route == path) {
              page.active = true;
              found = true;
            } else {
              page.active = false;
            }
          });
          item.active = found;
        } else {
          item.active = item.route == path;
        }
      });
    });
    $scope.menuResource = $resource('/api/navigation/menu');
    $scope.menus = $scope.menuResource.query();
    $scope.menuNotLoggedIn = true;
    $scope.isCollapsed = true;
  }
});