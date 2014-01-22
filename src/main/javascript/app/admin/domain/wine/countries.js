angular.module('mycellar.controllers.admin.domain.wine.countries', [
  'ngRoute',
  'mycellar.resources.wine.countries', 
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
      resourceName: 'Country', 
      resourcesName: 'Countries', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Pays',
      defaultSort: ['name'] 
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.countries').controller('AdminDomainCountriesController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Country', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.countries').controller('AdminDomainCountryController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.country = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Country', 
      resource: item
    });
  }
]);
