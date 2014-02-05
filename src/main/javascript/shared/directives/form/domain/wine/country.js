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
        country: '=',
        postLabel: '@'
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
        form: '=',
        country: '=',
        postLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      controller: [
        '$scope', '$location', 'Countries', 'AdminCountries',
        function($scope, $location, Countries, AdminCountries) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminCountries;
          } else {
            resource = Countries;
          }
          $scope.errors = [];
          $scope.countries = resource.like;
          $scope.new = function() {
            $scope.newCountry = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            $scope.errors = [];
            resource.validate($scope.newCountry, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function() {
                  if ($scope.subCountryForm[property] != undefined) {
                    $scope.subCountryForm[property].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.country = $scope.newCountry;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
