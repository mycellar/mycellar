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
        wine: '=',
        label: '@'
      },
      link: function(scope, element, attrs) {
        element[0].$.control.addEventListener('input', function() {
          scope.input = element[0].$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          element[0].possibles = value;
        });
        element[0].render = scope.renderWine;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setWine(value);
          scope.$apply();
        };
        element[0].value = scope.wine;
      },
      controller: [
        '$scope', '$location', 'Wines', 'AdminWines', '$filter',
        function($scope, $location, Wines, AdminWines, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminWines;
          } else {
            resource = Wines;
          }

          $scope.input = '';
          $scope.$watch('input', function() {
            if ($scope.input.length > 2) {
              resource.like($scope.input).then(function(value) {
                $scope.possibles = value;
              });
            } else {
              $scope.possibles = [];
            }
          });

          var wineFilter = $filter('wineRenderer');
          $scope.renderWine = function(wine) {
            if (wine != null) {
              return wineFilter(wine);
            } else {
              return '';
            }
          };
          $scope.setWine = function(wine) {
            if (wine != null) {
              resource.get({id: wine.id}, function(value) {
                $scope.wine = value;
              });
            } else {
              $scope.wine = null;
            }
          }
        }
      ]
    }
  }
]);
