angular.module('mycellar.controllers.admin.domain.user.users', [
  'ngRoute',
  'mycellar.resources.user.users', 
  'mycellar.directives.table', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'user', 
      resourceName: 'User', 
      resourcesName: 'Users', 
      groupLabel: 'Utilisateur', 
      resourcesLabel: 'Utilisateurs',
      defaultSort: ['lastname', 'firstname'],
      canCreate: false
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.user.users').controller('AdminDomainUsersController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'user', 
      resourceName: 'User', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.user.users').controller('AdminDomainUserController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.user = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'user',
      resourceName: 'User', 
      resource: item
    });
  }
]);
