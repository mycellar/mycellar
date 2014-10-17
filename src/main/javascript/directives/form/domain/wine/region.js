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
        region: '='
      }
    }
  }
]).directive('region', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/region.tpl.html',
      scope: {
        region: '=',
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
        autocomplete.render = scope.renderRegion;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setRegion(value);
          scope.$apply();
        };
        scope.$watch('region', function() {
          autocomplete.value = scope.region;
        });
      },
      controller: [
        '$scope', '$location', 'Regions', 'AdminRegions', '$filter',
        function($scope, $location, Regions, AdminRegions, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminRegions;
          } else {
            resource = Regions;
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

          var regionFilter = $filter('regionRenderer');
          $scope.renderRegion = function(region) {
            if (region != null) {
              return regionFilter(region);
            } else {
              return '';
            }
          };
          $scope.setRegion = function(region) {
            if (region != null) {
              resource.get({id: region.id}, function(value) {
                $scope.region = value;
              });
            } else {
              $scope.region = null;
            }
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newRegion = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newRegion = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newRegion, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subRegionForm[property] != undefined) {
                    $scope.subRegionForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.region = $scope.newRegion;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
