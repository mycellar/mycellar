angular.module('mycellar.controllers.admin.domain.wine.wines', [
  'ngRoute',
  'mycellar.resources.wine.wines', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'wine', 
      resourceName: 'Wine', 
      resourcesName: 'Wines', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Vins',
      defaultSort: ['producer.name',
                    'name', 
                    'vintage']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.wines').controller('AdminDomainWinesController', [
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Wine',
      items: items
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.wines').controller('AdminDomainWineController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.wine = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Wine',
      resource: item
    });
  }
]);
