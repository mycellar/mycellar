angular.module('mycellar.resources.wine.regions', [
  'mycellar.services.resource',
]);

angular.module('mycellar.resources.wine.regions').factory('Regions', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/regions',
      validateUrl: '/api/wine/validateRegion',
      likeUrl: '/api/wine/regions/like'
    });
  }
]);

angular.module('mycellar.resources.wine.regions').factory('AdminRegions', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/regions',
      validateUrl: '/api/admin/domain/wine/validateRegion',
      likeUrl: '/api/admin/domain/wine/regions/like'
    });
  }
]);
