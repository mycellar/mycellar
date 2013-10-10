angular.module('booking.contact', [
  'resources.contact.contacts',
  'resources.wine.producers', 
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/booking/contact/:producerId', {
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

angular.module('booking.contact').controller('ContactController', [
  '$scope', 'producer', 'Contacts',
  function($scope, producer, Contacts) {
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
      $scope.backup = {};
      angular.copy($scope.producer, $scope.backup);
      $scope.producer.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.producerForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.producer);
        } else if (value.internalError != undefined) {
          $scope.producerForm.$setValidity('Error occured: '+ value.internalError+'.', false);
          angular.copy($scope.backup, $scope.contact);
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
      $scope.backup = {};
      angular.copy($scope.contact, $scope.backup);
      $scope.contact.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.contactForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.contact);
        } else if (value.internalError != undefined) {
          $scope.contactForm.$setValidity('Error occured: '+ value.internalError+'.', false);
          angular.copy($scope.backup, $scope.contact);
        } else {
          $scope.backup = undefined;
          $scope.contact = null;
          $scope.setPage(1);
        }
      });
    };
    
    $scope.errors = [];
    $scope.cancelContact = function() {
      $scope.contact = null;
    };
  }
]);