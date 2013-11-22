angular.module('mycellar.controllers.admin.domain.wine.region', [
  'mycellar.resources.wine.regions',
  'mycellar.resources.wine.countries',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.region').controller('AdminDomainRegionController', [
  '$scope', 'region', 'adminDomainService', 'Countries',
  function ($scope, region, adminDomainService, Countries) {
    $scope.region = region;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Region', region, 'form'));
    $scope.countries = Countries.nameLike;
  }
]);
