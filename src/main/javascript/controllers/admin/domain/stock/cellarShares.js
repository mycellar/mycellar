angular.module('mycellar.controllers.admin.domain.stock.cellarShares', [
  'ngRoute',
  'mycellar.resources.stock.cellarShares',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'stock', 
      resourceName: 'CellarShare', 
      resourcesName: 'CellarShares', 
      groupLabel: 'Stockage', 
      resourcesLabel: 'Partages de cave',
      defaultSort: ['cellar.name', 'email']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellarShares').controller('AdminDomainCellarSharesController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stock',
      resourceName: 'CellarShare',
      canSearch: false,
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellarShares').controller('AdminDomainCellarShareController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.cellarShare = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'CellarShare', 
      resource: item
    });
  }
]);
