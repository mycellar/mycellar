angular.module('mycellar.resources.stack.stacks', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.stack.stacks').factory('AdminStacks', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/stack/stacks'
    });
  }
]);