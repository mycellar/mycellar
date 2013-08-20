angular.module('admin.domain.user.user', [
  'resources.user.users', 
  'services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.user.user').controller('AdminDomainUserController', [
  '$scope', 'user', 'adminDomainService',
  function ($scope, user, adminDomainService) {
    $scope.user = user;
    angular.extend($scope, adminDomainService.editMethods('user', 'User', user, 'form'));
  }
]);
