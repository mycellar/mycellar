angular.module('admin.domain.booking.bookingEvent', [
  'resources.booking.bookingEvents',
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.booking.bookingEvent').controller('AdminDomainBookingEventController', [
  '$scope', 'bookingEvent', 'adminDomainService',
  function ($scope, bookingEvent, adminDomainService) {
    $scope.bookingEvent = bookingEvent;
    angular.extend($scope, adminDomainService.editMethods('booking', 'BookingEvent', bookingEvent, 'form'));
  }
]);
