'use strict';

angular.module('mycellar').directive('mycellarNav', function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/nav.html',
  }
});

angular.module('mycellar').controller({
  NavigationController: function ($scope, $resource) {
    $scope.menuResource = $resource('/api/navigation/menu');
    $scope.menus = $scope.menuResource.query();
    $scope.menuNotLoggedIn = true;
  }
});