angular.module('mycellar.controllers.admin.domain.wine.wine', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav',
  'mycellar.resources.wine.producers',
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.regions',
  'mycellar.resources.wine.countries'
]);

angular.module('mycellar.controllers.admin.domain.wine.wine').controller('AdminDomainWineController', [
  '$scope', 'wine', 'adminDomainService', 'Producers', 'Appellations', 'Regions', 'Countries',
  function ($scope, wine, adminDomainService, Producers, Appellations, Regions, Countries) {
    $scope.wine = wine;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Wine', wine, 'form'));

    $scope.newProducer = function() {
      $scope.producer = {};
      $scope.showSubProducer = true;
    };
    $scope.cancelProducer = function() {
      $scope.showSubProducer = false;
    };
    $scope.okProducer = function() {
      Producers.validate($scope.producer, function (value, headers) {
        if (value.internalError != undefined) {
          $scope.subProducerForm.$setValidity('Error occured.', false);
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.subProducerForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
        } else {
          $scope.wine.producer = $scope.producer;
          $scope.showSubProducer = false;
        }
      });
    };
    $scope.producers = Producers.nameLike;
    $scope.newAppellation = function() {
      $scope.appellation = {};
      $scope.showSubAppellation = true;
    };
    $scope.cancelAppellation = function() {
      $scope.showSubAppellation = false;
    };
    $scope.okAppellation = function() {
      Appellations.validate($scope.appellation, function (value, headers) {
        if (value.internalError != undefined) {
          $scope.subAppellationForm.$setValidity('Error occured.', false);
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.subAppellationForm[value.properties[property]].$setValidity(value.errorKey, false);
          }
        } else {
          $scope.wine.appellation = $scope.appellation;
          $scope.showSubAppellation = false;
        }
      });
    };
    $scope.appellations = Appellations.nameLike;
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
