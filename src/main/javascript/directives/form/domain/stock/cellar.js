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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderCellar;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setCellar(value);
          scope.$apply();
        };
        scope.$watch('cellar', function() {
          autocomplete.value = scope.cellar;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newCellar = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newCellar = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newCellar, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subCellarForm[property] != undefined) {
                    $scope.subCellarForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.cellar = $scope.newCellar;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
