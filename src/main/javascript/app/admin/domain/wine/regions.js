angular.module('mycellar.controllers.admin.domain.wine.regions', [
  'ngRoute',
  'mycellar.resources.wine.regions', 
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
      resourceName: 'Region', 
      resourcesName: 'Regions', 
      groupLabel: 'Vin',
      resourcesLabel: 'Régions',
      defaultSort: ['country.name', 'name'] 
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.regions').controller('AdminDomainRegionsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Region', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.regions').controller('AdminDomainRegionController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.region = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Region', 
      resource: item
    });
  }
]);
