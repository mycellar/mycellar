angular.module('mycellar.controllers.admin.domain.booking.bookings', [
  'ngRoute',
  'mycellar.resources.booking.bookings', 
  'mycellar.directives.table', 
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
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookings').controller('AdminDomainBookingsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'Booking', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookings').controller('AdminDomainBookingController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.booking = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'Booking', 
      resource: item
    });
    
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
]);
