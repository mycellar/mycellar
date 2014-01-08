angular.module('mycellar.controllers.admin.domain.wine.formats', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.format', 
  'mycellar.resources.wine.formats', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Format', 'Formats', 'Vin', 'Formats')
      .whenCrud({}, {
        format: ['$route', 'Formats', function ($route, Formats) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Formats.getById(id);
          } else {
            return Formats.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.formats').controller('AdminDomainFormatsController', [
  '$scope', 'Formats', 'adminDomainService',
  function ($scope, Formats, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Format', Formats, ['capacity', 'name']));
  }
]);
