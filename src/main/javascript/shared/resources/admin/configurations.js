angular.module('mycellar.resources.admin.configurations', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.admin.configurations').factory('AdminConfigurations', [
  'domainResource',
  function(domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/admin/configurations'
    });
  }
]);
