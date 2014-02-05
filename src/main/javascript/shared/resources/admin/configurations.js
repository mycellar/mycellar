angular.module('mycellar.resources.admin.configurations', [
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.admin.configurations').factory('AdminConfigurations', [
  'adminDomainResource',
  function(adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/admin/configurations'
    });
  }
]);
