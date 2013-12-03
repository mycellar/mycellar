angular.module('mycellar.controllers.booking.reports', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/reports', {
      templateUrl: 'partials/booking/reports.tpl.html',
      controller: 'BookingReportsController'
    });
  }
]);

angular.module('mycellar.controllers.booking.reports').controller('BookingReportsController', [
  '$scope', 'BookingEvents', 'Bookings', 
  function($scope, BookingEvents, Bookings) {
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
        $scope.totalItems = value.count;
      });
    };

    $scope.itemsPerPage = 5;
    $scope.setPage(1);

    $scope.selectBookingEvent = function(bookingEvent) {
      $scope.bookingBottle = null;
      $scope.booking = null;
      $scope.bookingEvent = bookingEvent;
      $scope.quantities = BookingEvents.getQuantities(bookingEvent, function(value) {
        $scope.total = 0;
        angular.forEach($scope.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.quantities[$scope.bookingEvent.id + "-" + value.id];
        });
      });
      $scope.allBookings = Bookings.getByBookingEventId({bookingEventId: bookingEvent.id});
    };

    $scope.selectBookingBottle = function(bookingBottle) {
      $scope.bookingBottle = bookingBottle;
      $scope.booking = null;
      $scope.bookings = Bookings.getByBookingBottleId({bookingBottleId: bookingBottle.id}, function(value) {
        $scope.bookingBottleTotal = 0;
        angular.forEach($scope.bookings, function(value) {
          $scope.bookingBottleTotal += bookingBottle.price * value.quantities[value.bookingEvent.id + "-" + bookingBottle.id];
        });
      });
    };

    $scope.selectBooking = function(booking) {
      $scope.booking = booking;
      $scope.bookingTotal = 0;
      angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
        $scope.bookingTotal += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
      });
    };
  }
]);