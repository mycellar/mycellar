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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderCountry;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setCountry(value);
          scope.$apply();
        };
        scope.$watch('country', function() {
          autocomplete.value = scope.country;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newCountry = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newCountry = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newCountry, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subCountryForm[property] != undefined) {
                    $scope.subCountryForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.country = $scope.newCountry;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
