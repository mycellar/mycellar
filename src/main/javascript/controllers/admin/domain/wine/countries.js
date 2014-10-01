angular.module('mycellar.controllers.admin.domain.wine.countries', [
  'ngRoute',
  'mycellar.resources.wine.countries', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
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
  '$scope', 'adminDomainService', 'items',
  function ($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine',
      resourceName: 'Country', 
      items: items
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
