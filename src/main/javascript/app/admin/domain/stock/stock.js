angular.module('mycellar.controllers.admin.domain.stock.stock', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.stock.stock').controller('AdminDomainStockController', [
  '$scope', 'stock', 'adminDomainService',
  function ($scope, stock, adminDomainService) {
    $scope.stock = stock;
    angular.extend($scope, adminDomainService.editMethods('stock', 'Stock', stock, 'form'));
  }
]);
