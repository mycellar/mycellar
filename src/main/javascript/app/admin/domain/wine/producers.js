angular.module('mycellar.controllers.admin.domain.wine.producers', [
  'ngRoute',
  'mycellar.resources.wine.producers', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain({
      group: 'wine', 
      resourceName: 'Producer', 
      resourcesName: 'Producers', 
      groupLabel: 'Vin', 
      resourcesLabel: 'Producteurs',
      defaultSort: ['name']
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.producers').controller('AdminDomainProducersController', [
  '$scope', 'adminDomainService', 'tableContext',
  function ($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Producer', 
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.producers').controller('AdminDomainProducerController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.producer = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'wine', 
      resourceName: 'Producer', 
      resource: item
    });
  }
]);
