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
            count: 5
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.mybookings').controller('MyBookingsController', [
  '$scope', 'Bookings', 'bookings', '$anchorScroll',
  function($scope, Bookings, bookings, $anchorScroll) {
    $scope.itemsPerPage = 5;
    $scope.setPage = function() {
      $scope.firstItem = ($scope.currentPage - 1) * $scope.itemsPerPage;
      $scope.result = Bookings.getAllForCurrentUser({
        first: $scope.firstItem,
        count: $scope.itemsPerPage
      }, function(value) {
        if (value.list != null && value.list.length > 0) {
          $scope.selectBooking(value.list[0]);
        } else {
          $scope.selectBooking(null);
        }

        $scope.pageCount = Math.ceil(value.count / $scope.itemsPerPage);
        $scope.bookings = value.list;
        $scope.totalItems = value.count;
      });
    };

    $scope.selectBooking = function(booking) {
      $scope.booking = booking;
      $anchorScroll();
    };

    $scope.total = 0;
    $scope.$watch('booking.quantities', function (value) {
      $scope.total = 0;
      if ($scope.booking != null && $scope.booking.quantities != undefined) {
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);

    if (bookings.list != null && bookings.list.length > 0) {
      $scope.selectBooking(bookings.list[0]);
    } else {
      $scope.selectBooking(null);
    }

    $scope.pageCount = Math.ceil(bookings.count / $scope.itemsPerPage);
    $scope.bookings = bookings.list;
    $scope.totalItems = bookings.count;
  }
]);