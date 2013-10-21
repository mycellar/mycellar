angular.module('mycellar.controllers.admin.domain.admin.configurations', [
  'mycellar.controllers.admin.domain.admin.configuration', 
  'mycellar.resources.admin.configurations', 
  'mycellar.directives.table',
  'mycellar.directives.error', 
  'mycellar.directives.admin-domain-nav',
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

angular.module('mycellar.controllers.admin.domain.admin.configurations').controller('AdminDomainConfigurationsController', [
  '$scope', 'Configurations', 'adminDomainService', 
  function ($scope, Configurations, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('admin', 'Configuration', Configurations, ['key'], false, false));
  }
]);
