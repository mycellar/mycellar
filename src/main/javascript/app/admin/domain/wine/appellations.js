angular.module('mycellar.controllers.admin.domain.wine.appellations', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.appellation', 
  'mycellar.resources.wine.appellations', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
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

angular.module('mycellar.controllers.admin.domain.wine.appellations').controller('AdminDomainAppellationsController', [
  '$scope', 'Appellations', 'adminDomainService',
  function ($scope, Appellations, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Appellation', Appellations, ['region.country.name',
                                                                                                'region.name',
                                                                                                'name']));
  }
]);
