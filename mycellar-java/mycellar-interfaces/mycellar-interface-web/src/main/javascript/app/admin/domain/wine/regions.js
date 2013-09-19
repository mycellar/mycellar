angular.module('admin.domain.wine.regions', [
  'admin.domain.wine.region', 
  'resources.wine.regions', 
  'directives.table',
  'directives.error',
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Region', 'Regions', 'wine', 'Regions')
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

angular.module('admin.domain.wine.regions').controller('AdminDomainRegionsController', [
  '$scope', 'Regions', 'adminDomainService',
  function ($scope, Regions, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Region', Regions, ['country.name',
                                                                                      'name']));
  }
]);
