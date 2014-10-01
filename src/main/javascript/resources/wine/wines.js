angular.module('mycellar.resources.wine.wines', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.wine.wines').factory('Wines', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/wines',
      validateUrl: '/api/wine/validateWine'
    });
  }
]);

angular.module('mycellar.resources.wine.wines').factory('AdminWines', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/wines',
      validateUrl: '/api/admin/domain/wine/validateWine'
    });
  }
]);
