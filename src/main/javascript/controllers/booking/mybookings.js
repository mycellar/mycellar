angular.module('mycellar.controllers.booking.mybookings', [
  'ngRoute',
  'mycellar.resources.booking.bookings'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/mybookings', {
      templateUrl: 'partials/views/booking/mybookings.tpl.html',
      controller: 'MyBookingsController',
      resolve: {
        bookings: ['Bookings', function(Bookings) {
          return Bookings.getAllForCurrentUser({
            first: 0,
            count: 10
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.mybookings').controller('MyBookingsController', [
  '$scope', 'Bookings', 'bookings',
  function($scope, Bookings, bookings) {
    $scope.more = function() {
      var parameters = {
        first: $scope.bookings.length, 
        count: 10
      };
      if ($scope.search != null && $scope.search != '') {
        parameters['input'] = $scope.search;
      }
      return Bookings.getAllForCurrentUser(parameters, function(value) {
        $scope.bookings = $scope.bookings.concat(value.list);
      });
    };

    $scope.selectBooking = function(booking) {
      $scope.booking = booking;
      // TODO scroll to div[main] top
    };

    if (bookings != null && bookings.list.length > 0) {
      $scope.bookings = bookings.list;
      $scope.size = bookings.count;
      $scope.selectBooking($scope.bookings[0]);
    } else {
      $scope.selectBooking(null);
    }

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