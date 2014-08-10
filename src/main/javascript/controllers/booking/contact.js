angular.module('mycellar.controllers.booking.contact', [
  'ngRoute',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.contact.contacts',
  'mycellar.resources.wine.producers', 
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/contact/:producerId?', {
      templateUrl: 'partials/views/booking/contact.tpl.html',
      controller: 'ContactController',
      resolve: {
        producer: ['$route', 'AdminProducers', function ($route, Producers) {
          var id = $route.current.params.producerId;
          if (id != null && id > 0) {
            return Producers.get({id: id}).$promise;
          } else {
            return null;
          }
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.contact').controller('ContactController', [
  '$scope', 'producer', 'AdminContacts', '$filter',
  function($scope, producer, Contacts, $filter) {
    $scope.errors = [];
    $scope.setPage = function(page) {
      $scope.currentPage = page;
      $scope.firstItem = ($scope.currentPage - 1) * $scope.itemsPerPage;
      $scope.result = Contacts.get({
        first: $scope.firstItem,
        count: $scope.itemsPerPage,
        sort: ['current,desc'],
        filters: ['producer.id,'+$scope.producer.id]
      }, function(value) {
        $scope.pageCount = Math.ceil(value.count / $scope.itemsPerPage);
        $scope.contacts = value.list;
      });
    };

    $scope.producer = producer;
    $scope.itemsPerPage = 10;
    $scope.$watch('producer.id', function(value) {
      if (value != undefined) {
        $scope.setPage(1);
      }
    });

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

    $scope.$watch('dates.contactCurrent', function() {
      if ($scope.contact != null) {
        $scope.contact.current = $filter('date')($scope.dates.contactCurrent, 'yyyy-MM-dd');
      }
    });
    $scope.$watch('dates.contactNext', function() {
      if ($scope.contact != null) {
        $scope.contact.next = $filter('date')($scope.dates.contactNext, 'yyyy-MM-dd');
      }
    });

    $scope.editContact = function(contact) {
      Contacts.get({id: contact.id}, function(value) {
        $scope.contact = value;
        $scope.dates = {
          contactCurrent: new Date($scope.contact.current),
          contactNext: $scope.contact.next != null ? new Date($scope.contact.next) : null
        };
      });
    };

    $scope.addContact = function() {
      $scope.contact = new Contacts();
      $scope.contact.producer = $scope.producer;
      $scope.dates = {
        contactCurrent: new Date(),
        contactNext: null
      };
    };

    $scope.saveContact = function() {
      $scope.errors = [];
      $scope.backup = {};
      angular.copy($scope.contact, $scope.backup);
      $scope.contact.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          angular.forEach(value.properties, function(property) {
            if ($scope.contactForm[property] != undefined) {
              $scope.contactForm[property].$setValidity(value.errorKey, false);
            }
          });
          $scope.errors.push(value);
        } else {
          $scope.backup = undefined;
          $scope.contact = null;
          $scope.setPage(1);
        }
      });
    };

    $scope.cancelContact = function() {
      $scope.contact = null;
    };
  }
]);