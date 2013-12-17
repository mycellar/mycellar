angular.module('mycellar.directives.form.domain.wine.wine', [
  'mycellar.directives.form.domain.wine.appellation',
  'mycellar.directives.form.domain.wine.producer',
  'mycellar.resources.wine.wines'
]);

angular.module('mycellar.directives.form.domain.wine.wine').directive('wineForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/wine-form.tpl.html',
      scope: {
        form: '=',
        wine: '=',
        postLabel: '@'
      }
    }
  }
]).directive('wine', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/wine.tpl.html',
      scope: {
        form: '=',
        wine: '=',
        postLabel: '@'
      },
      controller: function($scope, Wines) {
        $scope.wines = Wines.like;
        $scope.new = function() {
          $scope.newWine = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Wines.validate($scope.newWine, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subWineForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subWineForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.wine = $scope.newWine;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
