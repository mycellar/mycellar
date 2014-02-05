angular.module('mycellar.resources.booking.bookingEvents', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.booking.bookingEvents').factory('BookingEvents', [
  '$resource', '$q',
  function($resource, $q) {
    var BookingEvents = $resource('/api/booking/bookingEvents/:id', {id: '@id'}, {
      currents: {
        url: '/api/booking/currentBookingEvents',
        method: 'GET'
      },
      validate: {
        url: '/api/booking/validateBookingEvent',
        method: 'POST'
      }
    });

    BookingEvents.count = function() {
      var deferred = $q.defer();
      BookingEvents.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    BookingEvents.nameLike = function(name) {
      var deferred = $q.defer();
      BookingEvents.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return BookingEvents;
  }
]);

angular.module('mycellar.resources.booking.bookingEvents').factory('AdminBookingEvents', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
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
