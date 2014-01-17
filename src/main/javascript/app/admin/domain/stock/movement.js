angular.module('mycellar.controllers.admin.domain.stock.movement', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.stock.movement').controller('AdminDomainMovementController', [
  '$scope', 'movement', 'adminDomainService',
  function ($scope, movement, adminDomainService) {
    $scope.movement = movement;
    angular.extend($scope, adminDomainService.editMethods('stock', 'Movement', movement, 'form'));
  }
]);
