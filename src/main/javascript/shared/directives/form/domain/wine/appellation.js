angular.module('mycellar.directives.form.domain.wine.appellation', [
  'mycellar.directives.form.domain.wine.region',
  'mycellar.resources.wine.appellations'
]);

angular.module('mycellar.directives.form.domain.wine.appellation').directive('appellationForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/appellation-form.tpl.html',
      scope: {
        form: '=',
        appellation: '=',
        postLabel: '@'
      }
    }
  }
]).directive('appellation', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/appellation.tpl.html',
      scope: {
        form: '=',
        appellation: '=',
        postLabel: '@'
      },
      controller: function($scope, Appellations) {
        $scope.appellations = Appellations.nameLike;
        $scope.new = function() {
          $scope.newAppellation = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Appellations.validate($scope.newAppellation, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subAppellationForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subAppellationForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.appellation = $scope.newAppellation;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
