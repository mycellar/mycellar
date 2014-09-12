angular.module('mycellar.controllers.admin.domain.user.users', [
  'ngRoute',
  'mycellar.resources.user.users', 
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
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'user', 
      resourceName: 'User', 
      items: items
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
