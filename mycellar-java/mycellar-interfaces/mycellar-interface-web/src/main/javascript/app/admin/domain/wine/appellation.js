angular.module('mycellar.controllers.admin.domain.wine.appellation', [
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.countries',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService', 'Countries',
  function ($scope, appellation, adminDomainService, Countries) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));
    $scope.regions = Regions.nameLike;
  }
]);
