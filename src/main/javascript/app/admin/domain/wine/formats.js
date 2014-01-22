angular.module('mycellar.controllers.admin.domain.wine.formats', [
  'ngRoute',
  'mycellar.resources.wine.formats', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'wine', 
      resourceName: 'Format', 
      resourcesName: 'Formats', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Formats',
      defaultSort: ['capacity', 'name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.formats').controller('AdminDomainFormatsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Format', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.formats').controller('AdminDomainFormatController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.format = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Format', 
      resource: item
    });
  }
]);
