angular.module('mycellar.controllers.booking.reports', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/reports', {
      templateUrl: 'partials/views/booking/reports.tpl.html',
      controller: 'BookingReportsController',
      resolve: {
        bookingEvents: ['AdminBookingEvents', function(AdminBookingEvents) {
          return AdminBookingEvents.get({
            first: 0,
            count: 5,
            sort: ['start,desc']
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.reports').controller('BookingReportsController', [
  '$scope', 'AdminBookingEvents', 'AdminBookings', '$anchorScroll', 'bookingEvents',
  function($scope, AdminBookingEvents, AdminBookings, $anchorScroll, bookingEvents) {
    $scope.pageChanged = function() {
      $scope.firstItem = ($scope.currentPage - 1) * $scope.itemsPerPage;
      $scope.result = AdminBookingEvents.get({
        first: $scope.firstItem,
        count: $scope.itemsPerPage,
        sort: ['start,desc']
      }, function(value) {
        if (value.list != null && value.list.length > 0) {
          $scope.selectBookingEvent(value.list[0]);
        }

        $scope.pageCount = Math.ceil(value.count / $scope.itemsPerPage);
        $scope.bookingEvents = value.list;
        $scope.totalItems = value.count;
      });
    };

    $scope.itemsPerPage = 5;

    $scope.selectBookingEvent = function(bookingEvent) {
      $scope.bookingBottle = null;
      $scope.booking = null;
      $scope.bookingEvent = bookingEvent;
      $scope.quantities = AdminBookingEvents.getQuantities({id: bookingEvent.id}, function(value) {
        $scope.total = 0;
        angular.forEach($scope.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.quantities[$scope.bookingEvent.id + "-" + value.id];
        });
      });
      $scope.allBookings = AdminBookings.getByBookingEventId({bookingEventId: bookingEvent.id});
      $anchorScroll();
    };

    $scope.selectBookingBottle = function(bookingBottle) {
      $scope.bookingBottle = bookingBottle;
      $scope.booking = null;
      $scope.bookings = AdminBookings.getByBookingBottleId({bookingBottleId: bookingBottle.id}, function(value) {
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
    
    if (bookingEvents.list != null && bookingEvents.list.length > 0) {
      $scope.selectBookingEvent(bookingEvents.list[0]);
    } else {
      $scope.selectBookingEvent(null);
    }

    $scope.pageCount = Math.ceil(bookingEvents.count / $scope.itemsPerPage);
    $scope.bookingEvents = bookingEvents.list;
    $scope.totalItems = bookingEvents.count;
  }
]);