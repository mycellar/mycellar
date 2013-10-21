angular.module('admin.domain.stack.stack', [
  'resources.stack.stacks', 
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/domain/stack/stack/:stackId', {
      templateUrl: 'partials/admin/domain/stack/stack.tpl.html',
      controller: 'AdminDomainStackController',
      resolve: {
        stack: ['Stacks', '$route', function(Stacks, $route) {
          return Stacks.getById($route.current.params.stackId);
        }]
      }
    });
  }
]);

angular.module('admin.domain.stack.stack').controller('AdminDomainStackController', [
  '$scope', 'stack', 'adminDomainService', 
  function ($scope, stack, adminDomainService) {
    $scope.stack = stack;
    angular.extend($scope, adminDomainService.editMethods('stack', 'Stack', stack, 'form'));
  }
]);
