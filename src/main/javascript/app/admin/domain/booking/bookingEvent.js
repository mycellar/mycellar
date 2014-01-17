angular.module('mycellar.controllers.admin.domain.booking.bookingEvent', [
  'mycellar.resources.booking.bookingEvents',
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.booking.bookingEvent').controller('AdminDomainBookingEventController', [
  '$scope', 'bookingEvent', 'adminDomainService',
  function ($scope, bookingEvent, adminDomainService) {
    $scope.bookingEvent = bookingEvent;
    angular.extend($scope, adminDomainService.editMethods('booking', 'BookingEvent', bookingEvent, 'form'));
  }
]);
