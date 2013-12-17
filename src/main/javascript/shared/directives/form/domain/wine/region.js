angular.module('mycellar.directives.form.domain.wine.region', [
  'mycellar.directives.form.domain.wine.country',
  'mycellar.resources.wine.regions'
]);

angular.module('mycellar.directives.form.domain.wine.region').directive('regionForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/region-form.tpl.html',
      scope: {
        form: '=',
        region: '=',
        postLabel: '@'
      }
    }
  }
]).directive('region', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/region.tpl.html',
      scope: {
        form: '=',
        region: '=',
        postLabel: '@'
      },
      controller: function($scope, Regions) {
        $scope.regions = Regions.nameLike;
        $scope.new = function() {
          $scope.newRegion = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Regions.validate($scope.newRegion, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subRegionForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subRegionForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.region = $scope.newRegion;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
