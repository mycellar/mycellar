'use strict';

angular.module('mycellar').controller({
  AdminDomainBookingEventsController: function ($scope, $location, $route, bookingEventService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: bookingEventService.resource.list,
      defaultSort: ['start', 'start']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/booking/bookingEvent/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/booking/bookingEvent/');
    };
    $scope.delete = function(itemId) {
      bookingEventService.resource.item.delete({bookingEventId: itemId}, function (value, headers) {
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
  AdminDomainBookingEventController: function ($scope, $resource, $route, $location) {
    var bookingEventId = $route.current.params.bookingEventId;
    $scope.bookingEventResource = $resource('/api/domain/booking/bookingEvent/:bookingEventId');
    if (bookingEventId != null && bookingEventId > 0) {
      $scope.bookingEvent = $scope.bookingEventResource.get({bookingEventId: bookingEventId});
    } else {
      $scope.bookingEvent = new $scope.bookingEventResource();
    }
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.bookingEvent, $scope.backup);
      $scope.bookingEvent.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/booking/bookingEvents/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.bookingEvent);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.bookingEvent);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/booking/bookingEvents/');
    };
  }
});