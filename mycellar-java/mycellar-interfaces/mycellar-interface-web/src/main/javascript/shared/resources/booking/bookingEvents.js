angular.module('resources.booking.bookingEvents', ['ngResource']);

angular.module('resources.booking.bookingEvents').factory('BookingEvents', ['$resource', '$q', function ($resource, $q) {

  var BookingEvents = $resource('/api/domain/booking/bookingEvents', {}, {
    currents: {
      url: '/api/booking/currentBookingEvents',
      method: 'GET'
    }
  });
  var BookingEvent = $resource('/api/domain/booking/bookingEvent/:bookingEventId');

  BookingEvent.deleteById = BookingEvent.delete;
  BookingEvent.delete = function(fn) {
    return BookingEvent.deleteById({bookingEventId: this.id}, fn);
  };
  BookingEvents.deleteById = function(id, fn) {
    return BookingEvent.deleteById({bookingEventId: id}, fn);
  };

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
  
  BookingEvents.getById = function(id) {
    return BookingEvent.get({bookingEventId: id});
  };
  
  BookingEvents.new = function() {
    return new BookingEvent();
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
