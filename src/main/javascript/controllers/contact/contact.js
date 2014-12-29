angular.module('mycellar.controllers.contact.contact', [
  'ngRoute',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.contact.contacts',
  'mycellar.resources.wine.producers', 
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/contact/contact/:contactId', {
      templateUrl: 'partials/views/contact/contact.tpl.html',
      controller: 'ContactController',
      resolve: {
        contact: [
          '$route', 'AdminContacts', 'Producers', '$q',
          function ($route, Contacts, Producers, $q) {
            return Contacts.get({id: $route.current.params.contactId}).$promise;
          }
        ]
      }
    });
    $routeProvider.when('/contact/contact/producer/:producerId', {
      templateUrl: 'partials/views/contact/contact.tpl.html',
      controller: 'ContactController',
      resolve: {
        contact: [
          '$route', 'AdminContacts', 'Producers', '$q', '$filter',
          function ($route, Contacts, Producers, $q, $filter) {
            var deferred = $q.defer();
            var contact = new Contacts();
            contact.current = $filter('date')(new Date(), 'yyyy-MM-dd');
            contact.producer = Producers.get({id: $route.current.params.producerId});
            contact.producer.$promise.then(function(value, reason) {
              if (value != null && value.id != null) {
                deferred.resolve(contact);
              } else {
                deferred.reject(reason);
              }
              return contact;
            });
            return deferred.promise;
          }
        ]
      }
    });
  }
]);

angular.module('mycellar.controllers.contact.contact').controller('ContactController', [
  '$scope', 'contact', '$location', '$filter',
  function($scope, contact, $location, $filter) {
    $scope.errors = [];
    $scope.contact = contact;
    $scope.contactCurrent = new Date($scope.contact.current);
    $scope.contactNext = new Date($scope.contact.next);
    $scope.$watch('contactCurrent', function() {
      $scope.contact.current = $filter('date')($scope.contactCurrent, 'yyyy-MM-dd');
    });
    $scope.$watch('contactNext', function() {
      $scope.contact.next = $filter('date')($scope.contactNext, 'yyyy-MM-dd');
    });

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
          angular.copy($scope.backup, $scope.contact);
        } else {
          $scope.backup = undefined;
          $location.path('/contact/producer/' + $scope.contact.producer.id);
        }
      });
    };
    
    $scope.cancelContact = function() {
      $location.path('/contact/producer/' + $scope.contact.producer.id);
    }
  }
]);