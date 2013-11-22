angular.module('mycellar.controllers.booking.reports', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/reports', {
      templateUrl: 'partials/booking/reports.tpl.html',
      controller: 'BookingReportsController',
      resolve: {
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.reports').controller('BookingReportsController', [
  '$scope', 'BookingEvents', 
  function($scope, BookingEvents) {
    $scope.setPage = function(page) {
      $scope.currentPage = page;
      $scope.firstItem = ($scope.currentPage - 1) * $scope.itemsPerPage;
      $scope.result = BookingEvents.get({
        first: $scope.firstItem,
        count: $scope.itemsPerPage,
        sort: ['start,desc']
      }, function(value) {
        $scope.pageCount = Math.ceil(value.count / $scope.itemsPerPage);
        $scope.bookingEvents = value.list;
        $scope.selectBookingEvent($scope.bookingEvents[0]);
      });
    };

    $scope.itemsPerPage = 5;
    $scope.setPage(1);

    $scope.selectBookingEvent = function(bookingEvent) {
      $scope.bookingEvent = bookingEvent;
      $scope.quantities = BookingEvents.getQuantities(bookingEvent, function(value) {
        $scope.total = 0;
        angular.forEach($scope.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.quantities[$scope.bookingEvent.id + "-" + value.id];
        });
      });
    };
  }
]);