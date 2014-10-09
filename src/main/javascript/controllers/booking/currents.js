angular.module('mycellar.controllers.booking.currents', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents',
  'mycellar.resources.booking.bookings'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/currents', {
      templateUrl: 'partials/views/booking/currents.tpl.html',
      controller: 'CurrentBookingsController',
      resolve: {
        startingData: ['BookingEvents', 'Bookings', '$q', function(BookingEvents, Bookings, $q){
          var deferred = $q.defer();
          var startingData = {
            bookingEvents: BookingEvents.currents()
          };
          startingData.bookingEvents.$promise.then(function(value) {
            if (value != null && value.list.length > 0) {
              startingData.booking = Bookings.getByBookingEventForCurrentUser({bookingEventId: value.list[0].id});
              startingData.booking.$promise.then(function() {
                deferred.resolve(startingData);
              }, function(reason) {
                deferred.reject(reason);
              });
            } else {
              startingData.booking = null;
              deferred.resolve(startingData);
            }
            return startingData;
          });
          return deferred.promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.currents').controller('CurrentBookingsController', [
  '$scope', 'startingData', 'Bookings', '$location',
  function($scope, startingData, Bookings, $location) {
    if (startingData.bookingEvents.list != undefined && startingData.bookingEvents.list.length > 0) {
      $scope.bookingEvents = startingData.bookingEvents.list;
    }
    if (startingData.booking != null) {
      $scope.booking = startingData.booking;
    }

    $scope.selectBooking = function(bookingEvent) {
      $scope.booking = Bookings.getByBookingEventForCurrentUser({bookingEventId: bookingEvent.id});
      // TODO scroll to div[main] top
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
      $scope.booking.$delete(function (value) {
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
      $scope.total = 0;
      if ($scope.booking != null && $scope.booking.quantities != undefined) {
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);
  }
]);