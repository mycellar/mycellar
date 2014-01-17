angular.module('mycellar.controllers.cellar.drink', [
  'ngRoute',
  'mycellar.directives.form.domain.stock.cellar',
  'mycellar.directives.form.domain.wine.format',
  'mycellar.directives.form.domain.wine.wine'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/drinkBottles', {
      templateUrl: 'partials/cellar/drink.tpl.html',
      controller: 'DrinkController',
      resolve: {
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.drink').controller('DrinkController', [
  '$scope', 'Stocks', '$location',
  function($scope, Stocks, $location) {
    $scope.errors = [];
    $scope.edit = function(drinkBottle) {
      $scope.drinkBottle = drinkBottle;
    };
    $scope.add = function() {
      $scope.drinkBottle = {
        bottle: {
          wine: null,
          format: null
        },
        quantity: 0,
        cellar: 0
      };
    };
    $scope.remove = function(drinkBottle) {
      $scope.drink.drinkBottles.splice($scope.drink.drinkBottles.indexOf(drinkBottle), 1);
    };
    $scope.addBottle = function() {
      if ($scope.isNew()) {
        $scope.drink.drinkBottles.push($scope.drinkBottle);
      }
      $scope.drinkBottle = null;
    };
    $scope.isNew = function() {
      return $scope.drink.drinkBottles.indexOf($scope.drinkBottle) == -1;
    };

    $scope.save = function() {
      $scope.errors = [];
      Stocks.drink($scope.drink, function(value) {
        if (value.errorKey != undefined) {
          angular.forEach(value.properties, function(property) {
            if ($scope.drinkForm[property]) {
              $scope.drinkForm[property].$setValidity(value.errorKey, false);
            }
          });
          $scope.errors.push(value);
        } else {
          $location.path('/cellar/io');
        }
      });
    };
    $scope.cancel = function() {
      $scope.drink = {
          drinkBottles: [],
          drinkWith: '',
          date: null
      };
      $scope.drinkBottle = null;
    }
    $scope.cancel();
  }
]);
