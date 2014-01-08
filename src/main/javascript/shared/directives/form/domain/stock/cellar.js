angular.module('mycellar.directives.form.domain.stock.cellar', [
  'mycellar.resources.stock.cellars'
]);

angular.module('mycellar.directives.form.domain.stock.cellar').directive('cellarForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/stock/cellar-form.tpl.html',
      scope: {
        form: '=',
        cellar: '=',
        postLabel: '@'
      }
    }
  }
]).directive('cellar', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/stock/cellar.tpl.html',
      scope: {
        form: '=',
        cellar: '=',
        postLabel: '@'
      },
      controller: function($scope, Cellars) {
        $scope.cellars = Cellars.nameLike;
        $scope.new = function() {
          $scope.newCellar = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Cellars.validate($scope.newCellar, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subCellarForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subCellarForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.cellar = $scope.newCellar;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
