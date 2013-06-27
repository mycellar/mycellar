'use strict';

angular.module('mycellar').directive('mycellarAdminNav', function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/admin/nav.html',
  }
});

angular.module('mycellar').controller({
  AdminNavigationController: function ($scope, $resource) {
    $scope.headers = [{label: 'wine', menus: [{label: 'Wine', route: '/admin/domain/wine/wines'},
                                              {label: 'Country', route: '/admin/domain/wine/countries'}]},
                      {label: 'stack', menus: [{label: 'Stack', route: '/admin/domain/stack/stacks'}]}];
  }
});