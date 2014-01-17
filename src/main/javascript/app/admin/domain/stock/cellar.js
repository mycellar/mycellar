angular.module('mycellar.controllers.admin.domain.stock.cellar', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.stock.cellar').controller('AdminDomainCellarController', [
  '$scope', 'cellar', 'adminDomainService',
  function ($scope, cellar, adminDomainService) {
    $scope.cellar = cellar;
    angular.extend($scope, adminDomainService.editMethods('stock', 'Cellar', cellar, 'form'));
  }
]);
