angular.module('mycellar.controllers.admin.domain.stock.cellars', [
  'ngRoute',
  'mycellar.controllers.admin.domain.stock.cellar',
  'mycellar.resources.stock.cellars',
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('stock', 'Cellar', 'Cellars', 'Stockage', 'Caves')
      .whenCrud({}, {
        cellar: ['$route', 'Cellars', function ($route, Cellars) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Cellars.getById(id);
          } else {
            return Cellars.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.cellars').controller('AdminDomainCellarsController', [
  '$scope', 'Cellars', 'adminDomainService',
  function ($scope, Cellars, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('stock', 'Cellar', Cellars, ['name']));
  }
]);
