'use strict';

function bookingServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      resource: {
        list: $resource('/api/domain/booking/bookings'),
        item: $resource('/api/domain/booking/booking/:bookingId')
      }
    };
  };
};

function bookingEventServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (bookingEventName) {
        return $http.get("/api/domain/booking/bookingEvent?count=15&filters=name,"+bookingEventName+"&first=0&sort=name,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/booking/bookingEvents'),
        item: $resource('/api/domain/booking/bookingEvent/:bookingEventId')
      }
    };
  };
};

angular.module('mycellar')
  .provider('bookingService', bookingServiceProvider)
  .provider('bookingEventService', bookingEventServiceProvider);
