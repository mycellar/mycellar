angular.module('mycellar.controllers.admin.domain.wine.region', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.region').controller('AdminDomainRegionController', [
  '$scope', 'region', 'adminDomainService',
  function ($scope, region, adminDomainService) {
    $scope.region = region;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Region', region, 'form'));
  }
]);
