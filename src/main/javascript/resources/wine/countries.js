angular.module('mycellar.resources.wine.countries', [
  'ngResource',
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.wine.countries').factory('Countries', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/wine/countries',
      validateUrl: '/api/wine/validateCountry'
    });
  }
]);

angular.module('mycellar.resources.wine.countries').factory('AdminCountries', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/wine/countries',
      validateUrl: '/api/admin/domain/wine/validateCountry'
    });
  }
]);
