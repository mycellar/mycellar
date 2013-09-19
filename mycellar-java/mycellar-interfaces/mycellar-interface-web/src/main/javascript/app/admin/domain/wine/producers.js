angular.module('admin.domain.wine.producers', [
  'admin.domain.wine.producer', 
  'resources.wine.producers', 
  'directives.table',
  'directives.error',
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('wine', 'Producer', 'Producers', 'wine', 'Producers')
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

angular.module('admin.domain.wine.producers').controller('AdminDomainProducersController', [
  '$scope', 'Producers', 'adminDomainService',
  function ($scope, Producers, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('wine', 'Producer', Producers, ['name']));
  }
]);
