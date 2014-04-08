angular.module('mycellar.resources.stock.movements', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.stock.movements').factory('Movements', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/stock/movements',
      validateUrl: '/api/stock/validateMovement'
    }, {
      getAllForCellar: {
        url: '/api/stock/movements',
        method: 'GET'
      }
    });
  }
]);

angular.module('mycellar.resources.stock.movements').factory('AdminMovements', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/stock/movements',
      validateUrl: '/api/admin/domain/stock/validateMovement'
    });
  }
]);
