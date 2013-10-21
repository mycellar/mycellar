angular.module('admin.domain.admin.configuration', [
  'resources.admin.configurations', 
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/domain/admin/configuration/:configurationId', {
      templateUrl: 'partials/admin/domain/admin/configuration.tpl.html',
      controller: 'AdminDomainConfigurationController',
      resolve: {
        configuration: ['Configurations', '$route', function(Configurations, $route) {
          return Configurations.getById($route.current.params.configurationId);
        }]
      }
    });
  }
]);

angular.module('admin.domain.admin.configuration').controller('AdminDomainConfigurationController', [
  '$scope', 'configuration', 'adminDomainService', 
  function ($scope, configuration, adminDomainService) {
    $scope.configuration = configuration;
    angular.extend($scope, adminDomainService.editMethods('admin', 'Configuration', configuration, 'form'));
  }
]);
