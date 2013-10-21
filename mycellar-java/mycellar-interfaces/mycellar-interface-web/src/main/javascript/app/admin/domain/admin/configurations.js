angular.module('admin.domain.admin.configurations', [
  'admin.domain.admin.configuration', 
  'resources.admin.configurations', 
  'directives.table',
  'directives.error', 
  'directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('admin', 'Configuration', 'Configurations', 'admin', 'Configurations')
      .whenCrud({}, {
        configuration: ['$route', 'Configurations', function ($route, Configurations) {
          return Configurations.getById($route.current.params.id);
        }]
      }
    );
  }
]);

angular.module('admin.domain.admin.configurations').controller('AdminDomainConfigurationsController', [
  '$scope', 'Configurations', 'adminDomainService', 
  function ($scope, Configurations, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('admin', 'Configuration', Configurations, ['key'], false, false));
  }
]);
