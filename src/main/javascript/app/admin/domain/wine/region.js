angular.module('mycellar.controllers.admin.domain.wine.region', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav',
  'mycellar.resources.wine.countries'
]);

angular.module('mycellar.controllers.admin.domain.wine.region').controller('AdminDomainRegionController', [
  '$scope', 'region', 'adminDomainService', 'Countries',
  function ($scope, region, adminDomainService, Countries) {
    $scope.region = region;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Region', region, 'form'));

    $scope.newCountry = function() {
      $scope.country = {};
      $scope.showSubCountry = true;
    };
    $scope.cancelCountry = function() {
      $scope.showSubCountry = false;
    };
    $scope.okCountry = function() {
      Countries.validate($scope.country, function (value, headers) {
        if (value.internalError != undefined) {
          $scope.subCountryForm.$setValidity('Error occured.', false);
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.subCountryForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
        } else {
          $scope.region.country = $scope.country;
          $scope.showSubCountry = false;
        }
      });
    };
    $scope.countries = Countries.nameLike;
  }
]);
