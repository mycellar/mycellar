angular.module('mycellar.resources.wine.formats', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.wine.formats').factory('Formats', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/formats',
      validateUrl: '/api/wine/validateFormat',
      likeUrl: '/api/wine/formats/like'
    });
  }
]);

angular.module('mycellar.resources.wine.formats').factory('AdminFormats', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/formats',
      validateUrl: '/api/admin/domain/wine/validateFormat',
      likeUrl: '/api/admin/domain/wine/formats/like'
    });
  }
]);
