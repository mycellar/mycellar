angular.module('mycellar.controllers.admin.domain.stack.stacks', [
  'ngRoute',
  'mycellar.resources.stack.stacks', 
  'mycellar.directives.error', 
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'stack', 
      resourceName: 'Stack', 
      resourcesName: 'Stacks', 
      groupLabel :'Stack', 
      resourcesLabel: 'Stacks',
      defaultSort: ['count', 'count'],
      canSave: false
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.stack.stacks').controller('AdminDomainStacksController', [
  '$scope', '$route', 'adminDomainService', 'items', 'AdminStacks',
  function ($scope, $route, adminDomainService, items, AdminStacks) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stack', 
      resourceName: 'Stack',
      canSearch: false,
      items: items
    });
    $scope.deleteAll = function() {
      AdminStacks.deleteAll({}, function (value, headers) {
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

angular.module('mycellar.controllers.admin.domain.stack.stacks').controller('AdminDomainStackController', [
  '$scope', 'adminDomainService', 'item', 
  function ($scope, adminDomainService, item) {
    $scope.stack = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'stack', 
      resourceName: 'Stack',
      resource: item
    });
  }
]);

