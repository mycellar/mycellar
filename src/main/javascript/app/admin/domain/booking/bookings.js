angular.module('mycellar.controllers.admin.domain.booking.bookings', [
  'ngRoute',
  'mycellar.controllers.admin.domain.booking.booking', 
  'mycellar.resources.booking.bookings', 
  'mycellar.directives.table', 
  'mycellar.directives.error',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('booking', 'Booking', 'Bookings', 'Réservation', 'Réservations')
      .whenCrud({}, {
        booking: ['$route', 'Bookings', function ($route, Bookings) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Bookings.getById(id);
          } else {
            return Bookings.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookings').controller('AdminDomainBookingsController', [
  '$scope', 'Bookings', 'adminDomainService',
  function ($scope, Bookings, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('booking', 'Booking', Bookings, ['bookingEvent.start', 'bookingEvent.start', 'customer.lastname']));
  }
]);
