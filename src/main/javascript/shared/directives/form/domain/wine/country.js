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
      templateUrl: 'partials/directives/form/wine/country.tpl.html',
      scope: {
        form: '=',
        country: '=',
        postLabel: '@'
      },
      controller: function($scope, Countries) {
        $scope.countries = Countries.nameLike;
        $scope.new = function() {
          $scope.newCountry = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Countries.validate($scope.newCountry, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subCountryForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subCountryForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.country = $scope.newCountry;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
