angular.module('mycellar.controllers.admin.domain.wine.wine', [
  'mycellar.resources.wine.wines',
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.producers',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.wine').controller('AdminDomainWineController', [
  '$scope', 'wine', 'adminDomainService', 'Appellations', 'Producers',
  function ($scope, wine, adminDomainService, Appellations, Producers) {
    $scope.wine = wine;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Wine', wine, 'form'));
    $scope.appellations = Appellations.nameLike;
    $scope.producers = Producers.nameLike;
  }
]);
