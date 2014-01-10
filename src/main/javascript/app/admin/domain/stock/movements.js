angular.module('mycellar.controllers.admin.domain.stock.movements', [
  'ngRoute',
  'mycellar.controllers.admin.domain.stock.movement',
  'mycellar.resources.stock.movements',
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('stock', 'Movement', 'Movements', 'Stockage', 'Mouvements')
      .whenCrud({}, {
        movement: ['$route', 'Movements', function ($route, Movements) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Movements.getById(id);
          } else {
            return Movements.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.movements').controller('AdminDomainMovementsController', [
  '$scope', 'Movements', 'adminDomainService',
  function ($scope, Movements, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('stock', 'Movement', Movements, ['cellar.owner.email', 'cellar.name', 'date', 'date']));
  }
]);
