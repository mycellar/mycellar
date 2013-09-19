angular.module('admin.domain.wine.appellations', [
  'admin.domain.wine.appellation', 
  'resources.wine.appellations', 
  'directives.table',
  'directives.error',
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Appellation', 'Appellations', 'wine', 'Appellations')
      .whenCrud({}, {
        appellation: ['$route', 'Appellations', function ($route, Appellations) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Appellations.getById(id);
          } else {
            return Appellations.new();
          }
        }]
      }
    );
  }
]);

angular.module('admin.domain.wine.appellations').controller('AdminDomainAppellationsController', [
  '$scope', 'Appellations', 'adminDomainService',
  function ($scope, Appellations, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Appellation', Appellations, ['region.country.name',
                                                                                                'region.name',
                                                                                                'name']));
  }
]);
