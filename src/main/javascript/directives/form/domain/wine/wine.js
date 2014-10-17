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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderWine;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setWine(value);
          scope.$apply();
        };
        scope.$watch('wine', function() {
          autocomplete.value = scope.wine;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newWine = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newWine = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newWine, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subWineForm[property] != undefined) {
                    $scope.subWineForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.country = $scope.newWine;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
