angular.module('mycellar.controllers.admin.domain.wine.wines', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.wine', 
  'mycellar.resources.wine.wines', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Wine', 'Wines', 'Vin', 'Vins')
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

angular.module('mycellar.controllers.admin.domain.wine.wines').controller('AdminDomainWinesController', [
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
