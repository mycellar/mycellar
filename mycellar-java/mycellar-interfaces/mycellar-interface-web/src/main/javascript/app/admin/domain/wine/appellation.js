angular.module('admin.domain.wine.appellation', [
  'resources.wine.appellations',
  'resources.wine.countries',
  'services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService', 'Countries',
  function ($scope, appellation, adminDomainService, Countries) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));
    $scope.regions = Regions.nameLike;
  }
]);
