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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderAppellation;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setAppellation(value);
          scope.$apply();
        };
        autocomplete.value = scope.appellation;
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newAppellation = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newAppellation = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newAppellation, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subAppellationForm[property] != undefined) {
                    $scope.subAppellationForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.appellation = $scope.newAppellation;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
