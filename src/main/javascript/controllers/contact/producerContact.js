angular.module('mycellar.controllers.contact.producer', [
  'ngRoute',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.contact.contacts',
  'mycellar.resources.wine.producers'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/contact/producer/:producerId', {
      templateUrl: 'partials/views/contact/producerContact.tpl.html',
      controller: 'ProducerContactController',
      resolve: {
        producer: ['$route', 'AdminProducers', function ($route, Producers) {
          return Producers.get({id: $route.current.params.producerId}).$promise;
        }],
        contacts: ['$route', 'AdminContacts', function ($route, Contacts) {
          return Contacts.get({
            first: 0,
            count: 10,
            sort: ['current,desc'],
            filters: ['producer.id,' + $route.current.params.producerId]
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.contact.producer').controller('ProducerContactController', [
  '$scope', 'producer', 'contacts', 'AdminContacts', '$location',
  function($scope, producer, contacts, Contacts, $location) {
    $scope.more = function() {
      var parameters = {
          first: $scope.contacts.length,
          count: 10,
          sort: ['current,desc'],
          filters: ['producer.id,' + $scope.producer.id]
        };
      return Contacts.get(parameters, function(value) {
        $scope.contacts = $scope.contacts.concat(value.list);
        $scope.size = value.count;
      });
    };

    $scope.producer = producer;
    $scope.contacts = contacts.list;
    $scope.size = contacts.count;

    $scope.saveProducer = function() {
      $scope.errors = [];
      $scope.backup = {};
      angular.copy($scope.producer, $scope.backup);
      $scope.producer.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          angular.forEach(value.properties, function(property) {
            if ($scope.producerForm[property] != undefined) {
              $scope.producerForm[property].$setValidity(value.errorKey, false);
            }
          });
          $scope.errors.push(value);
        } else {
          $scope.backup = undefined;
        }
      });
    };

    $scope.deleteContact = function(contact) {
      Contacts.delete(contact);
    }

    $scope.editContact = function(contact) {
      $location.path('/contact/contact/' + contact.id);
    };

    $scope.addContact = function() {
      $location.path('/contact/contact/producer/' + $scope.producer.id);
    };
  }
]);