angular.module('admin.domain.booking.booking', [
  'resources.booking.bookings', 
  'resources.booking.bookingEvents',
  'resources.user.users',
  'services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.booking.booking').controller('AdminDomainBookingController', [
  '$scope', 'booking', 'BookingEvents', 'adminDomainService', 'Users',
  function ($scope, booking, BookingEvents, adminDomainService, Users) {
    $scope.booking = booking;
    
    angular.extend($scope, adminDomainService.editMethods('booking', 'Booking', booking, 'form'));
    
    $scope.bookingEvents = BookingEvents.nameLike;
    $scope.customers = Users.like;
    $scope.total = 0;
    $scope.$watch('booking.quantities', function (value) {
      if ($scope.booking.quantities != undefined) {
        $scope.total = 0;
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);
  }
]);
