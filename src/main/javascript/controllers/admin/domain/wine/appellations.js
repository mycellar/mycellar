angular.module('mycellar.controllers.admin.domain.wine.appellations', [
  'ngRoute',
  'mycellar.resources.wine.appellations', 
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
      defaultSort: ['name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.appellations').controller('AdminDomainAppellationsController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Appellation',
      items: items
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
