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
        element[0].$.control.addEventListener('input', function() {
          scope.input = element[0].$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          element[0].possibles = value;
        });
        element[0].render = scope.renderRegion;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setRegion(value);
          scope.$apply();
        };
        element[0].value = scope.region;
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
          }
        }
      ]
    }
  }
]);
