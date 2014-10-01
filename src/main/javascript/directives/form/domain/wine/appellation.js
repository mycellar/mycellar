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
        appellation: '='
      }
    }
  }
]).directive('appellation', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/appellation.tpl.html',
      scope: {
        appellation: '=',
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
        element[0].render = scope.renderAppellation;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setAppellation(value);
          scope.$apply();
        };
        element[0].value = scope.appellation;
      },
      controller: [
        '$scope', '$location', 'Appellations', 'AdminAppellations', '$filter',
        function($scope, $location, Appellations, AdminAppellations, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminAppellations;
          } else {
            resource = Appellations;
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

          var appellationFilter = $filter('appellationRenderer');
          $scope.renderAppellation = function(appellation) {
            if (appellation != null) {
              return appellationFilter(appellation);
            } else {
              return '';
            }
          };
          $scope.setAppellation = function(appellation) {
            if (appellation != null) {
              resource.get({id: appellation.id}, function(value) {
                $scope.appellation = value;
              });
            } else {
              $scope.appellation = null;
            }
          }
        }
      ]
    }
  }
]);
