angular.module('mycellar.controllers.admin.domain.wine.regions', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.region', 
  'mycellar.resources.wine.regions', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Region', 'Regions', 'Vin', 'Régions')
      .whenCrud({}, {
        region: ['$route', 'Regions', function ($route, Regions) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Regions.getById(id);
          } else {
            return Regions.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.regions').controller('AdminDomainRegionsController', [
  '$scope', 'Regions', 'adminDomainService',
  function ($scope, Regions, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Region', Regions, ['country.name',
                                                                                      'name']));
  }
]);
