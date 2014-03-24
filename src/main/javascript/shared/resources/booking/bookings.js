angular.module('mycellar.resources.booking.bookings', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.booking.bookings').factory('Bookings', [
  '$resource', '$q',
  function($resource, $q) {
    var Bookings = $resource('/api/booking/bookings/:id', {id: '@id'}, {
      getAllForCurrentUser: {
        url: '/api/booking/bookings',
        method: 'GET'
      },
      getByBookingEventForCurrentUser: {
        url: '/api/booking/bookingByBookingEvent?bookingEventId=:bookingEventId',
        method: 'GET'
      }
    });

    Bookings.count = function() {
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

    return Bookings;
  }
]);

angular.module('mycellar.resources.booking.bookings').factory('AdminBookings', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
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