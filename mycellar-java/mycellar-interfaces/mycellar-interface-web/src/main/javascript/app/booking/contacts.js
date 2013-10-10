angular.module('booking.contacts', [
  'resources.contact.contacts',
  'directives.table',
  'services.table',
  'booking.contact',
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

angular.module('booking.contacts').controller('ContactsController', [
  '$scope', 'Contacts', 'tableService', '$location',
  function($scope, Contacts, tableService, $location) {
    $scope.tableOptions = {
      itemResource: Contacts.getLastContacts,
      defaultSort: ['next']
    };
    $scope.tableContext = tableService.createTableContext();
    $scope.new = function() {
      // TODO manage producer via typeahead in contact page ?
      $location.path('/booking/contact/');
    };
    $scope.edit = function(contact) {
      $location.path('/booking/contact/' + contact.producer.id);
    };
  }
]);