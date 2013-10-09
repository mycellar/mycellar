angular.module('admin.domain.stack.stacks', [
  'admin.domain.stack.stack', 
  'resources.stack.stacks', 
  'directives.table',
  'directives.error', 
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('stack', 'Stack', 'Stacks', 'stack', 'Stacks')
      .whenCrud({}, {
        configuration: ['$route', 'Stacks', function ($route, Stacks) {
          return Stacks.getById($route.current.params.id);
        }]
      }
    );
  }
]);

angular.module('admin.domain.stack.stacks').controller('AdminDomainStacksController', [
  '$scope', '$route', 'Stacks', 'adminDomainService', 
  function ($scope, $route, Stacks, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('stack', 'Stack', Stacks, ['count', 'count'], true, false));
    $scope.deleteAll = function() {
      Stacks.delete({}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
    };
  }
]);
