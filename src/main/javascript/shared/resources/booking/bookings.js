angular.module('mycellar.resources.booking.bookings', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.booking.bookings').factory('Bookings', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/booking/bookings'
    }, {
      getAllForCurrentUser: {
        url: '/api/booking/bookings',
        method: 'GET'
      },
      getByBookingEventForCurrentUser: {
        url: '/api/booking/bookingByBookingEvent?bookingEventId=:bookingEventId',
        method: 'GET'
      }
    });
  }
]);

angular.module('mycellar.resources.booking.bookings').factory('AdminBookings', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/booking/bookings'
    }, {
      getByBookingBottleId: {
        url: '/api/admin/domain/booking/bookingsByBottle?bookingBottleId=:bookingBottleId',
        method: 'GET',
        isArray: true
      },
      getByBookingEventId: {
        url: '/api/admin/domain/booking/bookingsByEvent?bookingEventId=:bookingEventId',
        method: 'GET',
        isArray: true
      }
    });
  }
]);