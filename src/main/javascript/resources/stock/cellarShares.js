angular.module('mycellar.resources.stock.cellarShares', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.stock.cellarShares').factory('CellarShares', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/stock/cellarShares',
      validateUrl: '/api/stock/validateCellarShare'
    }, {
      getAllForCurrentUser: {
        url: '/api/stock/cellarShares',
        method: 'GET'
      }
    });
  }
]);

angular.module('mycellar.resources.stock.cellarShares').factory('AdminCellarShares', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/stock/cellarShares',
      validateUrl: '/api/admin/domain/stock/validateCellarShare'
    });
  }
]);
