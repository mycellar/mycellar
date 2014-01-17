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
      transclude: true,
      templateUrl: 'partials/directives/form/wine/wine.tpl.html',
      scope: {
        form: '=',
        wine: '=',
        postLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      controller: function($scope, Wines, $filter) {
        var wineFilter = $filter('wineRenderer');
        $scope.renderWine = function(wine) {
          if (wine != null) {
            return wineFilter(wine);
          } else {
            return '';
          }
        };
        $scope.errors = [];
        $scope.wines = Wines.like;
        $scope.new = function() {
          $scope.newWine = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          $scope.errors = [];
          Wines.validate($scope.newWine, function (value, headers) {
            if (value.errorKey != undefined) {
              angular.forEach(value.properties, function(property) {
                if ($scope.subWineForm[property] != undefined) {
                  $scope.subWineForm[value.properties[property]].$setValidity(value.errorKey, false);
                }
              });
              $scope.errors.push(value);
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
