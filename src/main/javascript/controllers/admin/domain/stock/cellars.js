angular.module('mycellar.controllers.admin.domain.stock.cellars', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'stock', 
      resourceName: 'Cellar', 
      resourcesName: 'Cellars', 
      groupLabel: 'Stockage', 
      resourcesLabel: 'Caves',
      defaultSort: ['name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellars').controller('AdminDomainCellarsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Cellar', 
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellars').controller('AdminDomainCellarController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.cellar = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Cellar', 
      resource: item
    });
  }
]);
