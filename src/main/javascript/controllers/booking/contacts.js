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
      templateUrl: 'partials/views/booking/contacts.tpl.html',
      controller: 'ContactsController',
      resolve: {
        tableContext: [
          'tableService', 'Contacts',
          function(tableService, Contacts) {
            var tableContext = tableService.createTableContext(Contacts.getLastContacts, ['next']);
            return tableContext.setPage(1).promise;
          }
        ]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.contacts').controller('ContactsController', [
  '$scope', '$location', 'tableContext',
  function($scope, $location, tableContext) {
    $scope.errors = [];
    $scope.tableContext = tableContext;
    $scope.new = function() {
      $location.path('/booking/contact/');
    };
    $scope.edit = function(contact) {
      $location.path('/booking/contact/' + contact.producer.id);
    };
  }
]);