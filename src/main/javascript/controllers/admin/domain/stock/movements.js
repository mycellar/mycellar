angular.module('mycellar.controllers.admin.domain.stock.movements', [
  'ngRoute',
  'mycellar.resources.stock.movements',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'stock', 
      resourceName: 'Movement', 
      resourcesName: 'Movements', 
      groupLabel: 'Stockage', 
      resourcesLabel: 'Mouvements',
      defaultSort: ['cellar.owner.email', 'cellar.name', 'date', 'date']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.movements').controller('AdminDomainMovementsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Movement',
      canSearch: false,
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.movements').controller('AdminDomainMovementController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.movement = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'stock', 
      resourceName: 'Movement', 
      resource: item
    });
  }
]);
