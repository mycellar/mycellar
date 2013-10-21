angular.module('mycellar.controllers.admin.domain.wine.countries', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.country', 
  'mycellar.resources.wine.countries', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin-domain-nav',
  'mycellar.services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Country', 'Countries', 'wine', 'Countries')
      .whenCrud({}, {
        country: ['$route', 'Countries', function ($route, Countries) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Countries.getById(id);
          } else {
            return Countries.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.countries').controller('AdminDomainCountriesController', [
  '$scope', 'Countries', 'adminDomainService',
  function ($scope, Countries, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Country', Countries, ['name']));
  }
]);
