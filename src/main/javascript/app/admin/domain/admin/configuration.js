angular.module('mycellar.controllers.admin.domain.admin.configuration', [
  'ngRoute',
  'mycellar.resources.admin.configurations', 
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.admin.configuration').controller('AdminDomainConfigurationController', [
  '$scope', 'configuration', 'adminDomainService', 
  function ($scope, configuration, adminDomainService) {
    $scope.configuration = configuration;
    angular.extend($scope, adminDomainService.editMethods('admin', 'Configuration', configuration, 'form'));
  }
]);
