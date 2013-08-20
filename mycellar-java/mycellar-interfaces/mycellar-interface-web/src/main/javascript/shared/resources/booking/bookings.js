angular.module('resources.booking.bookings', ['ngResource']);

angular.module('resources.booking.bookings').factory('Bookings', ['$resource', '$q', function ($resource, $q) {

  var Bookings = $resource('/api/domain/booking/bookings');
  var Booking = $resource('/api/domain/booking/booking/:bookingId');
  
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
  
  Bookings.getById = function(id) {
    return Booking.get({bookingId: id});
  };
  
  Bookings.new = function() {
    return new Booking();
  };

  return Bookings;
}]);
