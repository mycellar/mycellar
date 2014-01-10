angular.module('mycellar.controllers.admin.domain.stock.movement', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.stock.movement').controller('AdminDomainMovementController', [
  '$scope', 'movement', 'adminDomainService',
  function ($scope, movement, adminDomainService) {
    $scope.movement = movement;
    angular.extend($scope, adminDomainService.editMethods('stock', 'Movement', movement, 'form'));
  }
]);
