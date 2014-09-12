angular.module('mycellar.controllers.admin.domain.wine.regions', [
  'ngRoute',
  'mycellar.resources.wine.regions', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'wine', 
      resourceName: 'Region', 
      resourcesName: 'Regions', 
      groupLabel: 'Vin',
      resourcesLabel: 'Régions',
      defaultSort: ['name'] 
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.regions').controller('AdminDomainRegionsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Region', 
      items: items
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
