angular.module('mycellar.resources.wine.appellations', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.wine.appellations').factory('Appellations', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/appellations',
      validateUrl: '/api/wine/validateAppellation'
    });
  }
]);

angular.module('mycellar.resources.wine.appellations').factory('AdminAppellations', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/appellations',
      validateUrl: '/api/admin/domain/wine/validateAppellation'
    });
  }
]);
