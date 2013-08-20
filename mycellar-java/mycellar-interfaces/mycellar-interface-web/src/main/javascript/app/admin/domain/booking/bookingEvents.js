angular.module('admin.domain.booking.bookingEvents', [
  'admin.domain.booking.bookingEvent', 
  'resources.booking.bookingEvents', 
  'directives.table',
  'directives.error',
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('booking', 'BookingEvent', 'BookingEvents', 'booking', 'Booking Events')
      .whenCrud({}, {
        bookingEvent: ['$route', 'BookingEvents', function ($route, BookingEvents) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return BookingEvents.getById(id);
          } else {
            return BookingEvents.new();
          }
        }]
      }
    );
  }
]);

angular.module('admin.domain.booking.bookingEvents').controller('AdminDomainBookingEventsController', [
  '$scope', 'BookingEvents', 'adminDomainService',
  function ($scope, BookingEvents, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('booking', 'BookingEvent', BookingEvents, ['start', 'start']));
  }
]);
