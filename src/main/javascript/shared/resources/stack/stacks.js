angular.module('mycellar.resources.stack.stacks', [
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.stack.stacks').factory('AdminStacks', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/stack/stacks'
    });
  }
]);