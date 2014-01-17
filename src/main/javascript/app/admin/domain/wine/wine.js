angular.module('mycellar.controllers.admin.domain.wine.wine', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.wine').controller('AdminDomainWineController', [
  '$scope', 'wine', 'adminDomainService',
  function ($scope, wine, adminDomainService) {
    $scope.wine = wine;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Wine', wine, 'form'));
  }
]);
