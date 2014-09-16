angular.module('mycellar.resources.wine.producers', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.wine.producers').factory('Producers', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/producers',
      validateUrl: '/api/wine/validateProducer'
    });
  }]);

angular.module('mycellar.resources.wine.producers').factory('AdminProducers', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/producers',
      validateUrl: '/api/admin/domain/wine/validateProducer'
    });
  }
]);
