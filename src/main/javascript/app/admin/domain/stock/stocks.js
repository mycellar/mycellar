angular.module('mycellar.controllers.admin.domain.stock.stocks', [
  'ngRoute',
  'mycellar.controllers.admin.domain.stock.stock',
  'mycellar.resources.stock.stocks',
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider',
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('stock', 'Stock', 'Stocks', 'Stockage', 'Stocks')
      .whenCrud({}, {
        stock: ['$route', 'Stocks', function ($route, Stocks) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Stocks.getById(id);
          } else {
            return Stocks.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.stock.stocks').controller('AdminDomainStocksController', [
  '$scope', 'Stocks', 'adminDomainService',
  function ($scope, Stocks, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('stock', 'Stock', Stocks, ['cellar.owner.email', 'cellar.name']));
  }
]);
