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
    $scope.headers = [{label: 'booking', menus: [{label: 'Booking Event', route: '/admin/domain/booking/bookingEvents'},
                                              {label: 'Booking', route: '/admin/domain/booking/bookings'}]},
                      {label: 'wine', menus: [{label: 'Appellation', route: '/admin/domain/wine/appellations'},
                                              {label: 'Country', route: '/admin/domain/wine/countries'},
                                              {label: 'Producer', route: '/admin/domain/wine/producers'},
                                              {label: 'Region', route: '/admin/domain/wine/regions'},
                                              {label: 'Wine', route: '/admin/domain/wine/wines'}]},
                      {label: 'user', menus: [{label: 'User', route: '/admin/domain/user/users'}]},
                      {label: 'stack', menus: [{label: 'Stack', route: '/admin/domain/stack/stacks'}]}];
  }
});