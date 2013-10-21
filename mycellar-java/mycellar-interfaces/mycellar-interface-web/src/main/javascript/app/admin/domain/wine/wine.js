angular.module('admin.domain.wine.wine', [
  'resources.wine.wines',
  'resources.wine.appellations',
  'resources.wine.producers',
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.wine.wine').controller('AdminDomainWineController', [
  '$scope', 'wine', 'adminDomainService', 'Appellations', 'Producers',
  function ($scope, wine, adminDomainService, Appellations, Producers) {
    $scope.wine = wine;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Wine', wine, 'form'));
    $scope.appellations = Appellations.nameLike;
    $scope.producers = Producers.nameLike;
  }
]);
