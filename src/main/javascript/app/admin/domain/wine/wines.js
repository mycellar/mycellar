angular.module('mycellar.controllers.admin.domain.wine.wines', [
  'ngRoute',
  'mycellar.resources.wine.wines', 
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
      resourceName: 'Wine', 
      resourcesName: 'Wines', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Vins',
      defaultSort: ['appellation.region.country.name',
                    'appellation.region.name',
                    'appellation.name',
                    'producer.name',
                    'name', 
                    'vintage']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.wines').controller('AdminDomainWinesController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Wine',
      tableContext: tableContext
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
