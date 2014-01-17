angular.module('mycellar.controllers.admin.domain.user.users', [
  'ngRoute',
  'mycellar.controllers.admin.domain.user.user', 
  'mycellar.resources.user.users', 
  'mycellar.directives.table', 
  'mycellar.directives.error',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('user', 'User', 'Users', 'Utilisateur', 'Utilisateurs')
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

angular.module('mycellar.controllers.admin.domain.user.users').controller('AdminDomainUsersController', [
  '$scope', 'Users', 'adminDomainService',
  function ($scope, Users, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('user', 'User', Users, ['lastname', 'firstname'], true, false));
  }
]);
