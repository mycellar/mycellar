angular.module('mycellar.controllers.admin.domain.stack.stacks', [
  'ngRoute',
  'mycellar.controllers.admin.domain.stack.stack', 
  'mycellar.resources.stack.stacks', 
  'mycellar.directives.table',
  'mycellar.directives.error', 
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
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

angular.module('mycellar.controllers.admin.domain.stack.stacks').controller('AdminDomainStacksController', [
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

