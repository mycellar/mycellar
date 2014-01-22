angular.module('mycellar.controllers.admin.domain.stock.stocks', [
  'ngRoute',
  'mycellar.resources.stock.stocks',
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin'
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
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Stock', 
      tableContext: tableContext
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
