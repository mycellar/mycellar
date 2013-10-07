angular.module('booking.mybookings', [], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/mybookings', {
      templateUrl: 'partials/booking/mybookings.tpl.html',
      controller: 'MyBookingsController',
      resolve: {
        bookings: ['Bookings', function(Bookings){
          return Bookings.getAllForCurrentUser();
        }]
      }
    });
  }
]);

angular.module('booking.mybookings').controller('MyBookingsController', [
  '$scope', 'bookings', 
  function($scope, bookings) {
    $scope.bookingsResource = bookings;
    $scope.$watch('bookingsResource.list', function() {
      if ($scope.bookingsResource.list != undefined && $scope.bookingsResource.list.length > 0) {
        $scope.bookings = $scope.bookingsResource.list;
        $scope.selectBooking($scope.bookings[0]);
      }
    });

    $scope.selectBooking = function(booking) {
      $scope.booking = booking;
    };
    
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