angular.module('mycellar.controllers.admin.domain.booking.bookingEvents', [
  'ngRoute',
  'mycellar.resources.booking.bookingEvents', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'booking', 
      resourceName: 'BookingEvent', 
      resourcesName: 'BookingEvents', 
      groupLabel: 'Réservation', 
      resourcesLabel: 'Campagnes de réservation',
      defaultSort: ['start', 'start']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookingEvents').controller('AdminDomainBookingEventsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'BookingEvent',
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.booking.bookingEvents').controller('AdminDomainBookingEventController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.bookingEvent = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'booking', 
      resourceName: 'BookingEvent', 
      resource: item
    });
  }
]);
