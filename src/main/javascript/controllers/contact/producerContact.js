angular.module('mycellar.controllers.contact.producer', [
  'ngRoute',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.contact.contacts',
  'mycellar.resources.wine.producers'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/contact/producer/:producerId?', {
      templateUrl: 'partials/views/contact/producerContact.tpl.html',
      controller: 'ProducerContactController',
      resolve: {
        producer: ['$route', 'AdminProducers', function ($route, Producers) {
          var producerId = $route.current.params.producerId;
          if (producerId) {
            return Producers.get({id: producerId}).$promise;
          } else {
            return null;
          }
        }],
        contacts: ['$route', 'AdminContacts', function ($route, Contacts) {
          var producerId = $route.current.params.producerId;
          if (producerId) {
            return Contacts.get({
              first: 0,
              count: 5,
              sort: ['current,desc'],
              filters: ['producer.id,' + $route.current.params.producerId]
            }).$promise;
          } else {
            return {list: [], count: 0};
          }
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
          count: 5,
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
    $scope.edit = producer == null;

    $scope.modifyProducer = function() {
      $scope.edit = true;
      $scope.backup = {};
      angular.copy($scope.producer, $scope.backup);
    };
    
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
          angular.copy($scope.backup, $scope.producer);
        } else {
          $scope.backup = undefined;
          $scope.edit = false;
        }
      });
    };

    $scope.cancelProducer = function() {
      angular.copy($scope.backup, $scope.producer);
      if ($scope.producer == null) {
        $scope.back();
      } else {
        $scope.edit = false;
      }
    }

    $scope.deleteContact = function(contact) {
      Contacts.delete(contact);
    }

    $scope.editContact = function(contact) {
      $location.url('/contact/contact/' + contact.id);
    };

    $scope.addContact = function() {
      $location.url('/contact/contact/producer/' + $scope.producer.id);
    };

    $scope.back = function() {
      $location.url('/contact/contacts/');
    };

    $scope.$watch('producer', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        if (newValue != null && newValue.id != null) {
          var parameters = {
              first: $scope.contacts.length,
              count: 5,
              sort: ['current,desc'],
              filters: ['producer.id,' + newValue.id]
            };
          Contacts.get(parameters, function(value) {
            $scope.contacts = value.list;
            $scope.size = value.count;
          });
        } else {
          $scope.contacts = [];
          $scope.size = 0;
        }
      }
    });

    // fix two way data binding lost
    $scope.fix = {producer: $scope.producer};
    $scope.$watch('fix.producer', function() {
      $scope.producer = $scope.fix.producer;
    });
  }
]);
