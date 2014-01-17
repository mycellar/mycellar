angular.module('mycellar.controllers.cellar.arrival', [
  'ngRoute',
  'mycellar.directives.form.domain.stock.cellar',
  'mycellar.directives.form.domain.wine.format',
  'mycellar.directives.form.domain.wine.wine'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/packageArrival', {
      templateUrl: 'partials/cellar/arrival.tpl.html',
      controller: 'ArrivalController',
      resolve: {
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.arrival').controller('ArrivalController', [
  '$scope', 'Stocks', '$location',
  function($scope, Stocks, $location) {
    $scope.errors = [];
    $scope.edit = function(arrivalBottle) {
      $scope.arrivalBottle = arrivalBottle;
    };
    $scope.add = function() {
      $scope.arrivalBottle = {
        bottle: {
          wine: null,
          format: null
        },
        quantity: 0,
        price: 0
      };
    };
    $scope.remove = function(arrivalBottle) {
      $scope.arrival.arrivalBottles.splice($scope.arrival.arrivalBottles.indexOf(arrivalBottle), 1);
    };
    $scope.addBottle = function() {
      if ($scope.isNew()) {
        $scope.arrival.arrivalBottles.push($scope.arrivalBottle);
      }
      $scope.arrivalBottle = null;
    };
    $scope.isNew = function() {
      return $scope.arrival.arrivalBottles.indexOf($scope.arrivalBottle) == -1;
    };

    $scope.save = function() {
      $scope.errors = [];
      Stocks.arrival($scope.arrival, function(value) {
        if (value.errorKey != undefined) {
          angular.forEach(value.properties, function(property) {
            if ($scope.arrivalForm[property] != undefined) {
              $scope.arrivalForm[property].$setValidity(value.errorKey, false);
            }
          });
          $scope.errors.push(value);
        } else {
          $location.path('/cellar/io');
        }
      });
    };
    $scope.cancel = function() {
      $scope.arrival = {
          arrivalBottles: [],
          source: '',
          charges: 0,
          date: null
      };
      $scope.arrivalBottle = null;
    }
    $scope.cancel();
  }
]);
