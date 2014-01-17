angular.module('mycellar.controllers.admin.domain.wine.region', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.region').controller('AdminDomainRegionController', [
  '$scope', 'region', 'adminDomainService',
  function ($scope, region, adminDomainService) {
    $scope.region = region;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Region', region, 'form'));
  }
]);
