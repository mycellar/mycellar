angular.module('mycellar.controllers.admin.domain.booking.booking', [
  'mycellar.resources.booking.bookings', 
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.booking.booking').controller('AdminDomainBookingController', [
  '$scope', 'booking', 'adminDomainService',
  function ($scope, booking, adminDomainService) {
    $scope.booking = booking;
    
    angular.extend($scope, adminDomainService.editMethods('booking', 'Booking', booking, 'form'));
    
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
