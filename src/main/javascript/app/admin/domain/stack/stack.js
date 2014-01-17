angular.module('mycellar.controllers.admin.domain.stack.stack', [
  'ngRoute',
  'mycellar.resources.stack.stacks', 
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.stack.stack').controller('AdminDomainStackController', [
  '$scope', 'stack', 'adminDomainService', 
  function ($scope, stack, adminDomainService) {
    $scope.stack = stack;
    angular.extend($scope, adminDomainService.editMethods('stack', 'Stack', stack, 'form'));
  }
]);
