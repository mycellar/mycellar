angular.module('mycellar.controllers.admin.domain.stock.cellarShare', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.stock.cellarShare').controller('AdminDomainCellarShareController', [
  '$scope', 'cellarShare', 'adminDomainService',
  function ($scope, cellarShare, adminDomainService) {
    $scope.cellarShare = cellarShare;
    angular.extend($scope, adminDomainService.editMethods('stock', 'CellarShare', cellarShare, 'form'));
  }
]);
