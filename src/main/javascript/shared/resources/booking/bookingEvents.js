angular.module('mycellar.resources.booking.bookingEvents', ['ngResource']);

angular.module('mycellar.resources.booking.bookingEvents').factory('BookingEvents', ['$resource', '$q', function ($resource, $q) {

  var BookingEvents = $resource('/api/domain/booking/bookingEvents/:id', {id: '@id'}, {
    currents: {
      url: '/api/booking/currentBookingEvents',
      method: 'GET'
    },
    getQuantities: {
      url: '/api/booking/quantities/:id',
      method: 'GET'
    }
  });

  BookingEvents.count = function () {
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
}]);
