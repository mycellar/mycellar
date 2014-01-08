angular.module('mycellar.controllers.admin.domain.stock.cellarShares', [
  'ngRoute',
  'mycellar.controllers.admin.domain.stock.cellarShare',
  'mycellar.resources.stock.cellarShares',
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('stock', 'CellarShare', 'CellarShares', 'Stockage', 'Partages de cave')
      .whenCrud({}, {
        cellarShare: ['$route', 'CellarShares', function ($route, CellarShares) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return CellarShares.getById(id);
          } else {
            return CellarShares.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellarShares').controller('AdminDomainCellarSharesController', [
  '$scope', 'CellarShares', 'adminDomainService',
  function ($scope, CellarShares, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('stock', 'CellarShare', CellarShares, ['cellar.name', 'email']));
  }
]);
