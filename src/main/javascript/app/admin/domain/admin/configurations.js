angular.module('mycellar.controllers.admin.domain.admin.configurations', [
  'mycellar.resources.admin.configurations', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider) {
    adminDomainServiceProvider.forDomain({
      group: 'admin', 
      resourceName: 'Configuration', 
      resourcesName: 'Configurations', 
      groupLabel: 'Administration', 
      resourcesLabel: 'Configurations',
      defaultSort: ['key'],
      canCreate: false,
      canDelete: false
    }).whenCrud();
  }
]);

angular.module('mycellar.controllers.admin.domain.admin.configurations').controller('AdminDomainConfigurationsController', [
  '$scope', 'adminDomainService', 'tableContext',
  function($scope, adminDomainService, tableContext) {
    adminDomainService.listMethods({
      scope: $scope, 
      group: 'admin', 
      resourceName: 'Configuration',
      tableContext: tableContext
    });
  }
]);

angular.module('mycellar.controllers.admin.domain.admin.configurations').controller('AdminDomainConfigurationController', [
  '$scope', 'adminDomainService', 'item',
  function ($scope, adminDomainService, item) {
    $scope.configuration = item;
    adminDomainService.editMethods({
      scope: $scope,
      group: 'admin', 
      resourceName: 'Configuration', 
      resource: item
    });
  }
]);
