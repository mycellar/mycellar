angular.module('mycellar.controllers.admin.domain.wine.appellation', [
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.regions',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService', 'Regions',
  function ($scope, appellation, adminDomainService, Regions) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));
    $scope.regions = Regions.nameLike;
  }
]);
