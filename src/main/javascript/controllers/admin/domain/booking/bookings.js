angular.module('mycellar.controllers.admin.domain.booking.bookings', [
  'ngRoute',
  'mycellar.resources.booking.bookings', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'booking', 
      resourceName: 'Booking', 
      resourcesName: 'Bookings', 
      groupLabel: 'Réservation', 
      resourcesLabel: 'Réservations',
      defaultSort: ['bookingEvent.start', 'bookingEvent.start', 'customer.lastname']
    }).whenCrud({}, {
      bookingEvent: [
        'AdminBookingEvents', '$location',
        function(BookingEvents, $location) {
          var search = $location.search();
          if (search.bookingEvent != null) {
            return BookingEvents.get({id: search.bookingEvent}).$promise;
          } else {
            return null;
          }
        }
      ]
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookings').controller('AdminDomainBookingsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'Booking',
      canSearch: false,
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookings').controller('AdminDomainBookingController', [
  '$scope', 'adminDomainService', 'item', 'bookingEvent',
  function ($scope, adminDomainService, item, bookingEvent) {
    $scope.booking = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'Booking', 
      resource: item
    });

    $scope.total = 0;
    $scope.$watch('booking.bookingEvent', function(newValue, oldValue) {
      if (oldValue != newValue && $scope.booking.bookingEvent != undefined) {
        $scope.booking.quantities = {};
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id] = 0;
        });
      }
    });
    $scope.$watch('booking.quantities', function (value) {
      if ($scope.booking.quantities != undefined) {
        $scope.total = 0;
        angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
          $scope.total += value.price * $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id];
        });
      }
    }, true);

    if (bookingEvent != null) {
      $scope.booking.bookingEvent = bookingEvent;
      $scope.booking.quantities = {};
      angular.forEach($scope.booking.bookingEvent.bottles, function(value) {
        $scope.booking.quantities[$scope.booking.bookingEvent.id + "-" + value.id] = 0;
      });
    }

    // fix two way data binding lost
    $scope.fix = {
      bookingEvent: $scope.booking.bookingEvent,
      customer: $scope.booking.customer
    };
    $scope.$watch('fix.bookingEvent', function() {
      $scope.booking.bookingEvent = $scope.fix.bookingEvent;
    });
    $scope.$watch('fix.customer', function() {
      $scope.booking.customer = $scope.fix.customer;
    });
  }
]);
