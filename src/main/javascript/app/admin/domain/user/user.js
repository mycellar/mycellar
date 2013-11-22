angular.module('mycellar.controllers.admin.domain.user.user', [
  'mycellar.resources.user.users', 
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.user.user').controller('AdminDomainUserController', [
  '$scope', 'user', 'adminDomainService',
  function ($scope, user, adminDomainService) {
    $scope.user = user;
    angular.extend($scope, adminDomainService.editMethods('user', 'User', user, 'form'));
  }
]);
