angular.module('mycellar.controllers.admin.domain', [
  'ngRoute',
  'mycellar.controllers.admin.domain.admin',
  'mycellar.controllers.admin.domain.booking',
  'mycellar.controllers.admin.domain.contact',
  'mycellar.controllers.admin.domain.stack',
  'mycellar.controllers.admin.domain.stock',
  'mycellar.controllers.admin.domain.user',
  'mycellar.controllers.admin.domain.wine',
  'mycellar.directives.admin'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/domains', {
      templateUrl: 'partials/views/admin/domain.tpl.html',
      controller: 'AdminDomainController',
    });
  }
]);

angular.module('mycellar.controllers.admin.domain').controller('AdminDomainController', [
  function () {
  }
]);
