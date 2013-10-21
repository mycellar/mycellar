angular.module('admin.domain.wine.wines', [
  'admin.domain.wine.wine', 
  'resources.wine.wines', 
  'directives.table',
  'directives.error',
  'directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Wine', 'Wines', 'wine', 'Wines')
      .whenCrud({}, {
        wine: ['$route', 'Wines', function ($route, Wines) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Wines.getById(id);
          } else {
            return Wines.new();
          }
        }]
      }
    );
  }
]);

angular.module('admin.domain.wine.wines').controller('AdminDomainWinesController', [
  '$scope', 'Wines', 'adminDomainService',
  function ($scope, Wines, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Wine', Wines, ['appellation.region.country.name',
                                                                                  'appellation.region.name',
                                                                                  'appellation.name',
                                                                                  'producer.name',
                                                                                  'name', 
                                                                                  'vintage']));
  }
]);
