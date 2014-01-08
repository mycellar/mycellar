angular.module('mycellar.controllers.admin.domain.wine.wine', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.wine').controller('AdminDomainWineController', [
  '$scope', 'wine', 'adminDomainService',
  function ($scope, wine, adminDomainService) {
    $scope.wine = wine;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Wine', wine, 'form'));
  }
]);
