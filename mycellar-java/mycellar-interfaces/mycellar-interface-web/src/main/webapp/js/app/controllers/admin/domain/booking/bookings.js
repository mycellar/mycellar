'use strict';

angular.module('mycellar').controller({
  AdminDomainBookingsController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'bookingEvent.start',
        'customer.lastname'
      ],
      ways: {
        'bookingEvent.start': 'desc',
        'bookingEvent.end': null,
        'bookingEvent.name': null,
        'customer.lastname': 'asc'
      }
    };
    $scope.filters = {
      'bookingEvent.name': '',
      'customer.lastname': ''
    }
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/booking/bookings')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/booking/booking/' + itemId);
    };
    $scope.sortBy = function(property) {
      if ($scope.sort.ways[property] == 'asc') {
        $scope.sort.ways[property] = 'desc';
      } else if ($scope.sort.ways[property] == 'desc') {
        $scope.sort.properties.splice($scope.sort.properties.indexOf(property), 1);
        $scope.sort.ways[property] = null;
      } else {
        $scope.sort.properties.push(property);
        $scope.sort.ways[property] = 'asc';
      }
    };
    $scope.clearFilters = function() {
      for (var filter in $scope.filters) {
        $scope.filters[filter] = '';
      }
    };
  },
  AdminDomainBookingController: function ($scope, $resource, $route, $location, $http) {
    var bookingId = $route.current.params.bookingId;
    $scope.bookingResource = $resource('/api/domain/booking/booking/:bookingId');
    $scope.booking = $scope.bookingResource.get({bookingId: bookingId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.booking, $scope.backup);
      $scope.booking.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/booking/bookings/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.booking);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.booking);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/booking/bookings/');
    };
    
    $scope.bookingEvents = function (bookingEventName) {
      return $http.get("/api/domain/booking/bookingEvents?count=15&filters=name,"+bookingEventName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
    $scope.customers = function (customerName) {
      return $http.get("/api/domain/user/users?count=15&filters=lastname,"+customerName+"&first=0&sort=lastname,asc").then(function(response){
        return response.data.list;
      });
    };
    $scope.total = 0;
    $scope.$watch('booking.quantities', function (value) {
      if ($scope.booking.quantities != undefined) {
        $scope.total = 0;
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);
  }
});