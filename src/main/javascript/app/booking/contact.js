angular.module('mycellar.controllers.booking.contact', [
  'ngRoute',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.contact.contacts',
  'mycellar.resources.wine.producers', 
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/contact/:producerId?', {
      templateUrl: 'partials/booking/contact.tpl.html',
      controller: 'ContactController',
      resolve: {
        producer: ['$route', 'Producers', function ($route, Producers) {
          var id = $route.current.params.producerId;
          if (id != null && id > 0) {
            return Producers.getById(id);
          } else {
            return null;
          }
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.booking.contact').controller('ContactController', [
  '$scope', 'producer', 'Contacts',
  function($scope, producer, Contacts) {
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
    $scope.itemsPerPage = 5;
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
    
    $scope.editContact = function(contact) {
      $scope.contact = contact;
    };
    
    $scope.addContact = function() {
      $scope.contact = Contacts.new();
      $scope.contact.producer = $scope.producer;
      $scope.contact.current;
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