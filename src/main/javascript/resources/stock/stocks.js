angular.module('mycellar.resources.stock.stocks', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.stock.stocks').factory('Stocks', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/stock/stocks',
      validateUrl: '/api/stock/validateStock'
    }, {
      getWinesForCellar: {
        url: '/api/stock/wines',
        method: 'GET'
      },
      getAllForWine: {
        url: '/api/stock/stocks',
        method: 'GET'
      },
      arrival: {
        url: '/api/stock/arrival',
        method: 'POST'
      },
      drink: {
        url: '/api/stock/drink',
        method: 'POST'
      }
    });
  }
]);

angular.module('mycellar.resources.stock.stocks').factory('AdminStocks', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/stock/stocks',
      validateUrl: '/api/admin/domain/stock/validateStock'
    });
  }
]);