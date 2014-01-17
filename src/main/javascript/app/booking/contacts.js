angular.module('mycellar.controllers.booking.contacts', [
  'ngRoute',
  'mycellar.resources.contact.contacts',
  'mycellar.directives.table',
  'mycellar.services.table',
  'mycellar.controllers.booking.contact',
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/contacts', {
      templateUrl: 'partials/booking/contacts.tpl.html',
      controller: 'ContactsController',
      resolve: {
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.contacts').controller('ContactsController', [
  '$scope', 'Contacts', 'tableService', '$location',
  function($scope, Contacts, tableService, $location) {
    $scope.errors = [];
    $scope.tableOptions = {
      itemResource: Contacts.getLastContacts,
      defaultSort: ['next']
    };
    $scope.tableContext = tableService.createTableContext();
    $scope.new = function() {
      $location.path('/booking/contact/');
    };
    $scope.edit = function(contact) {
      $location.path('/booking/contact/' + contact.producer.id);
    };
  }
]);