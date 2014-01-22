angular.module('mycellar.controllers.admin.domain.contact.contacts', [
  'ngRoute',
  'mycellar.resources.contact.contacts', 
  'mycellar.directives.table', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'contact', 
      resourceName: 'Contact', 
      resourcesName: 'Contacts', 
      groupLabel: 'Contact', 
      resourcesLabel: 'Contacts',
      defaultSort: ['current', 'current', 'producer.name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.contact.contacts').controller('AdminDomainContactsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'contact', 
      resourceName: 'Contact', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.contact.contacts').controller('AdminDomainContactController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.contact = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'contact', 
      resourceName: 'Contact', 
      resource: item
    });
  }
]);
