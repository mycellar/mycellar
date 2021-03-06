angular.module('mycellar.resources.user.users', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.user.users').factory('AdminUsers', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/user/users',
      validateUrl: '/api/admin/domain/user/validateUser'
    }, {
      createUser: {
        url: '/api/admin/domain/user/createUser',
        method: 'POST'
      }
    });
  }
]);
