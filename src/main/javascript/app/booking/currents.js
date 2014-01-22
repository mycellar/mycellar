angular.module('mycellar.controllers.booking.currents', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents',
  'mycellar.resources.booking.bookings'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/currents', {
      templateUrl: 'partials/booking/currents.tpl.html',
      controller: 'CurrentBookingsController',
      resolve: {
        bookingEvents: ['BookingEvents', function(BookingEvents){
          return BookingEvents.currents().$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.currents').controller('CurrentBookingsController', [
  '$scope', 'bookingEvents', 'Bookings', '$location',
  function($scope, bookingEvents, Bookings, $location) {
    $scope.bookingEventsResource = bookingEvents;
    $scope.$watch('bookingEventsResource.list', function() {
      if ($scope.bookingEventsResource.list != undefined && $scope.bookingEventsResource.list.length > 0) {
        $scope.bookingEvents = $scope.bookingEventsResource.list;
        $scope.selectBooking($scope.bookingEvents[0]);
      }
    });

    $scope.selectBooking = function(bookingEvent) {
      $scope.booking = Bookings.getByBookingEventForCurrentUser(bookingEvent);
    };
    
    $scope.save = function(booking) {
      var errors = $scope.errors;
      $scope.backup = {};
      angular.copy($scope.booking, $scope.backup);
      $scope.booking.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          errors.push({errorKey: value.errorKey});
          angular.copy($scope.backup, $scope.booking);
        } else if (value.internalError != undefined) {
          errors.push({errorKey: value.internalError});
          angular.copy($scope.backup, $scope.booking);
        } else {
          $scope.backup = undefined;
          $location.path('booking/mybookings');
        }
      });
    };
    
    $scope.delete = function(booking) {
      var errors = $scope.errors;
      $scope.backup = {};
      angular.copy($scope.booking, $scope.backup);
      Bookings.deleteById($scope.booking.id, function (value, headers) {
        if (value.errorKey != undefined) {
          errors.push({errorKey: value.errorKey});
          angular.copy($scope.backup, $scope.booking);
        } else if (value.internalError != undefined) {
          errors.push({errorKey: value.internalError});
          angular.copy($scope.backup, $scope.booking);
        } else {
          $scope.backup = undefined;
          $location.path('booking/mybookings');
        }
      });
    };
    
    $scope.errors = [];
    $scope.total = 0;
    $scope.$watch('booking.quantities', function (value) {
      if ($scope.booking != null && $scope.booking.quantities != undefined) {
        $scope.total = 0;
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);
  }
]);