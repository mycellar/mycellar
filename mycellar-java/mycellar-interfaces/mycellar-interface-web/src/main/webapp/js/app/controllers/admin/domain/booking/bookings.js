'use strict';

angular.module('mycellar').controller({
  AdminDomainBookingsController: function ($scope, $location, $route, bookingService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: bookingService.resource.list,
      defaultSort: ['bookingEvent.start', 'bookingEvent.start', 'customer.lastname']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/booking/booking/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/booking/booking/');
    };
    $scope.delete = function(itemId) {
      bookingService.resource.item.delete({bookingId: itemId}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
    };
  },
  AdminDomainBookingController: function ($scope, $resource, $route, $location, $http) {
    var bookingId = $route.current.params.bookingId;
    $scope.bookingResource = $resource('/api/domain/booking/booking/:bookingId');
    if (bookingId != null && bookingId > 0) {
      $scope.booking = $scope.bookingResource.get({bookingId: bookingId});
    } else {
      $scope.booking = new $scope.bookingResource();
    }
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