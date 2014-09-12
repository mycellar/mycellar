angular.module('mycellar.controllers.contact.contacts', [
  'ngRoute',
  'mycellar.resources.contact.contacts',
  'mycellar.directives.table',
  'mycellar.services.table',
  'mycellar.controllers.contact.producer'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/contact/contacts', {
      templateUrl: 'partials/views/contact/contacts.tpl.html',
      controller: 'ContactsController',
      resolve: {
        contacts: [
          'Contacts',
          function(Contacts) {
            return Contacts.getLastContacts({
              first: 0,
              count: 20,
              sort: [
                'next,asc'
              ]
            }).$promise;
          }
        ]
      }
    });
  }
]);

angular.module('mycellar.controllers.contact.contacts').controller('ContactsController', [
  '$scope', '$location', 'contacts', 'Contacts',
  function($scope, $location, contacts, Contacts) {
    var getContacts = function(first, callback) {
      var parameters = {
        first: first, 
        count: 20, 
        sort: [
          'next,asc'
        ]
      };
      if ($scope.search != null && $scope.search.length > 2) {
        parameters['input'] = $scope.search;
      }
      return Contacts.getLastContacts(parameters, callback);
    };
    var contactsCallback = function(value) {
      $scope.contacts = value.list;
      $scope.size = value.count;
    };

    $scope.search = '';
    if (contacts != null) {
      contactsCallback(contacts);
    }

    $scope.more = function() {
      getContacts($scope.wines.length, function(value) {
        $scope.contacts = $scope.contacts.concat(value.list);
      });
    };

    $scope.new = function() {
      $location.path('/contact/producer/');
    };
    $scope.edit = function(contact) {
      $location.path('/contact/producer/' + contact.producer.id);
    };

    $scope.toggleHidden = function() {
      var hiddenElements = document.querySelectorAll('core-toolbar[main]>[hidden]');
      var notHiddenElements = document.querySelectorAll('core-toolbar[main]>:not([hidden])');
      angular.forEach(hiddenElements, function(element) {
        element.removeAttribute('hidden');
      });
      angular.forEach(notHiddenElements, function(element) {
        element.setAttribute('hidden', '');
      });
      var searchInput = document.querySelector('core-input#search');
      if (searchInput.hasAttribute('hidden')) {
        $scope.search = '';
      } else {
        searchInput.focus();
      }
    };

    $scope.clearSearch = function() {
      $scope.search = '';
      document.querySelector('core-input#search').focus();
    };

    $scope.$watch('search', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        return getContacts(0, contactsCallback);
      }
    });
  }
]);