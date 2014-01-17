angular.module('mycellar.controllers.admin.domain.wine.producers', [
  'ngRoute',
  'mycellar.controllers.admin.domain.wine.producer', 
  'mycellar.resources.wine.producers', 
  'mycellar.directives.table',
  'mycellar.directives.error',
  'mycellar.directives.admin',
  'mycellar.services.admin'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Producer', 'Producers', 'Vin', 'Producteurs')
      .whenCrud({}, {
        producer: ['$route', 'Producers', function ($route, Producers) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Producers.getById(id);
          } else {
            return Producers.new();
          }
        }]
      }
    );
  }
]);

angular.module('mycellar.controllers.admin.domain.wine.producers').controller('AdminDomainProducersController', [
  '$scope', 'Producers', 'adminDomainService',
  function ($scope, Producers, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Producer', Producers, ['name']));
  }
]);
