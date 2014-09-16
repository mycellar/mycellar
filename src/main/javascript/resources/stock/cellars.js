angular.module('mycellar.resources.stock.cellars', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.stock.cellars').factory('Cellars', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/stock/cellars',
      validateUrl: '/api/stock/validateCellar'
    });
  }
]);

angular.module('mycellar.resources.stock.cellars').factory('AdminCellars', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/stock/cellars',
      validateUrl: '/api/admin/domain/stock/validateCellar'
    });
  }
]);
