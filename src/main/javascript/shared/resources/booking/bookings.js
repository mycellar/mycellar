angular.module('mycellar.resources.booking.bookings', ['ngResource']);

angular.module('mycellar.resources.booking.bookings').factory('Bookings', [
  '$resource', '$q', 
  function ($resource, $q) {

    var Bookings = $resource('/api/domain/booking/bookings', {}, {
      getAllForCurrentUser: {
        url: '/api/booking/bookings',
        method: 'GET'
      },
      getByBookingBottleId: {
        url: '/api/booking/bookingsByBottle?bookingBottleId=:bookingBottleId',
        method: 'GET',
        isArray: true
      },
      getByBookingEventId: {
        url: '/api/booking/bookingsByEvent?bookingEventId=:bookingEventId',
        method: 'GET',
        isArray: true
      }
    });
    var Booking = $resource('/api/domain/booking/booking/:bookingId', {}, {
      getByBookingEventForCurrentUser: {
        url: '/api/booking/booking?bookingEventId=:bookingEventId',
        method: 'GET'
      }
    });

    Bookings.deleteById = function(id, fn) {
      return Booking.delete({bookingId: id}, fn);
    };

    Bookings.count = function () {
      var deferred = $q.defer();
      Bookings.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Bookings.getByBookingEventForCurrentUser = function(bookingEvent) {
      return Booking.getByBookingEventForCurrentUser({'bookingEventId': bookingEvent.id});
    };

    Bookings.getById = function(id) {
      return Booking.get({bookingId: id});
    };

    Bookings.new = function() {
      return new Booking();
    };

    return Bookings;
  }
]);
