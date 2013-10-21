angular.module('mycellar.controllers.admin.domain.booking.bookings', [
  'ngRoute',
  'mycellar.controllers.admin.domain.booking.booking', 
  'mycellar.resources.booking.bookings', 
  'mycellar.directives.table', 
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('booking', 'Booking', 'Bookings', 'booking', 'Bookings')
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
