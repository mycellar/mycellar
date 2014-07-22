angular.module('mycellar.controllers.admin.domain.wine.appellations', [
  'ngRoute',
  'mycellar.resources.wine.appellations', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'wine', 
      resourceName: 'Appellation', 
      resourcesName: 'Appellations', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Appellations',
      defaultSort: ['region.country.name', 'region.name', 'name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.appellations').controller('AdminDomainAppellationsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Appellation',
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.appellations').controller('AdminDomainAppellationController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.appellation = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Appellation', 
      resource: item
    });
  }
]);
