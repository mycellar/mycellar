angular.module('mycellar.controllers.admin.domain.stock.stocks', [
  'ngRoute',
  'mycellar.resources.stock.stocks',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'stock', 
      resourceName: 'Stock', 
      resourcesName: 'Stocks', 
      groupLabel: 'Stockage', 
      resourcesLabel: 'Stocks',
      defaultSort: ['cellar.owner.email', 'cellar.name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.stocks').controller('AdminDomainStocksController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Stock',
      canSearch: false,
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.stocks').controller('AdminDomainStockController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.stock = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Stock', 
      resource: item
    });
  }
]);
