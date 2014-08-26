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
  '$scope', 'AdminBookingEvents', 'AdminBookings', 'bookingEvents',
  function($scope, AdminBookingEvents, AdminBookings, bookingEvents) {
    $scope.more = function() {
      var parameters = {
        first: $scope.bookingEvents.length,
        count: 10,
        sort: ['start,desc']
      };
      if ($scope.search != null && $scope.search != '') {
        parameters['input'] = $scope.search;
      }
      return AdminBookingEvents.get(parameters, function(value) {
        $scope.bookingEvents = $scope.bookingEvents.concat(value.list);
      });
    };

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
      // TODO scroll to div[main] top
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
      $scope.bookingEvents = bookingEvents.list;
      $scope.size = bookingEvents.count;
      $scope.selectBookingEvent($scope.bookingEvents[0]);
    } else {
      $scope.selectBookingEvent(null);
    }
  }
]);