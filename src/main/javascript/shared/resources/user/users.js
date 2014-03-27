angular.module('mycellar.resources.user.users', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.user.users').factory('Users', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/user/users',
      validateUrl: '/api/user/validateUser',
      likeUrl: '/api/user/users/like'
    });
  }
]);

angular.module('mycellar.resources.user.users').factory('AdminUsers', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/user/users',
      validateUrl: '/api/admin/domain/user/validateUser',
      likeUrl: '/api/admin/domain/user/users/like'
    });
  }
]);
