angular.module('admin.domain.user.users', [
  'admin.domain.user.user', 
  'resources.user.users', 
  'directives.table', 
  'directives.error',
  'directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('user', 'User', 'Users', 'user', 'Users')
      .whenCrud({}, {
        user: ['$route', 'Users', function ($route, Users) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Users.getById(id);
          } else {
            return Users.new();
          }
        }]
      }
    );
  }
]);

angular.module('admin.domain.user.users').controller('AdminDomainUsersController', [
  '$scope', 'Users', 'adminDomainService',
  function ($scope, Users, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('user', 'User', Users, ['lastname', 'firstname'], true, false));
  }
]);
