angular.module('mycellar.resources.booking.bookingEvents', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.booking.bookingEvents').factory('BookingEvents', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/booking/bookingEvents'
    }, {
      currents: {
        url: '/api/booking/currentBookingEvents',
        method: 'GET'
      }
    });
  }
]);

angular.module('mycellar.resources.booking.bookingEvents').factory('AdminBookingEvents', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/booking/bookingEvents',
      validateUrl: '/api/admin/domain/booking/validateBookingEvent',
      likeUrl: '/api/admin/domain/booking/bookingEvents/like'
    }, {
      getQuantities: {
        url: '/api/admin/domain/booking/quantities/:id',
        method: 'GET'
      }
    });
  }
]);
