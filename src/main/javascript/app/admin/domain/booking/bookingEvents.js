angular.module('mycellar.controllers.admin.domain.booking.bookingEvents', [
  'ngRoute',
  'mycellar.controllers.admin.domain.booking.bookingEvent', 
  'mycellar.resources.booking.bookingEvents', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('booking', 'BookingEvent', 'BookingEvents', 'Réservation', 'Campagnes de réservation')
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

angular.module('mycellar.controllers.admin.domain.booking.bookingEvents').controller('AdminDomainBookingEventsController', [
  '$scope', 'BookingEvents', 'adminDomainService',
  function ($scope, BookingEvents, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('booking', 'BookingEvent', BookingEvents, ['start', 'start']));
  }
]);
