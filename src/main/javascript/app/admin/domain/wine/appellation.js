angular.module('mycellar.controllers.admin.domain.wine.appellation', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav',
  'mycellar.resources.wine.regions',
  'mycellar.resources.wine.countries'
]);

angular.module('mycellar.controllers.admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService', 'Regions', 'Countries',
  function ($scope, appellation, adminDomainService, Regions, Countries) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));

    $scope.newRegion = function() {
      $scope.region = {};
      $scope.showSubRegion = true;
    };
    $scope.cancelRegion = function() {
      $scope.showSubRegion = false;
    };
    $scope.okRegion = function() {
      Regions.validate($scope.region, function (value, headers) {
        if (value.internalError != undefined) {
          $scope.subRegionForm.$setValidity('Error occured.', false);
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.subRegionForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
        } else {
          $scope.appellation.region = $scope.region;
          $scope.showSubRegion = false;
        }
      });
    };
    $scope.regions = Regions.nameLike;
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
