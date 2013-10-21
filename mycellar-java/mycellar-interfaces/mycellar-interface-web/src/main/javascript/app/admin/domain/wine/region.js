angular.module('admin.domain.wine.region', [
  'resources.wine.regions',
  'resources.wine.countries',
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.wine.region').controller('AdminDomainRegionController', [
  '$scope', 'region', 'adminDomainService', 'Countries',
  function ($scope, region, adminDomainService, Countries) {
    $scope.region = region;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Region', region, 'form'));
    $scope.countries = Countries.nameLike;
  }
]);
