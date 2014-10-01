angular.module('mycellar.directives.form.domain.wine.country', [
  'mycellar.resources.wine.countries'
]);

angular.module('mycellar.directives.form.domain.wine.country').directive('countryForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/country-form.tpl.html',
      scope: {
        form: '=',
        country: '='
      }
    }
  }
]).directive('country', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/country.tpl.html',
      scope: {
        country: '=',
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
        element[0].render = scope.renderCountry;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setCountry(value);
          scope.$apply();
        };
        element[0].value = scope.country;
      },
      controller: [
        '$scope', '$location', 'Countries', 'AdminCountries', '$filter',
        function($scope, $location, Countries, AdminCountries, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminCountries;
          } else {
            resource = Countries;
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

          var countryFilter = $filter('countryRenderer');
          $scope.renderCountry = function(country) {
            if (country != null) {
              return countryFilter(country);
            } else {
              return '';
            }
          };
          $scope.setCountry = function(country) {
            if (country != null) {
              resource.get({id: country.id}, function(value) {
                $scope.country = value;
              });
            } else {
              $scope.country = null;
            }
          }
        }
      ]
    }
  }
]);
