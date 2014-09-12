angular.module('mycellar.controllers.admin.domain.admin.configurations', [
  'mycellar.resources.admin.configurations', 
  'mycellar.directives.error',
  'mycellar.directives.form',
  'mycellar.directives.admin',
  'mycellar.services.admin.domain'
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
  '$scope', 'adminDomainService', 'items',
  function($scope, adminDomainService, items) {
    adminDomainService.listMethods({
      scope: $scope, 
      group: 'admin', 
      resourceName: 'Configuration',
      items: items
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
