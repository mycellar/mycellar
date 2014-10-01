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
        cellar: '='
      }
    }
  }
]).directive('cellar', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/stock/cellar.tpl.html',
      scope: {
        cellar: '=',
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
        element[0].render = scope.renderCellar;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setCellar(value);
          scope.$apply();
        };
        element[0].value = scope.cellar;
      },
      controller: [
        '$scope', '$location', 'Cellars', 'AdminCellars',
        function($scope, $location, Cellars, AdminCellars) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminCellars;
          } else {
            resource = Cellars;
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

          $scope.renderCellar = function(cellar) {
            if (cellar != null) {
              return cellar.name;
            } else {
              return '';
            }
          };
          $scope.setCellar = function(cellar) {
            if (cellar != null) {
              resource.get({id: cellar.id}, function(value) {
                $scope.cellar = value;
              });
            } else {
              $scope.cellar = null;
            }
          }
        }
      ]
    }
  }
]);
