angular.module('admin.domain', [
    'admin.domain.admin',
    'admin.domain.booking',
    'admin.domain.contact',
    'admin.domain.stack',
    'admin.domain.user',
    'admin.domain.wine',
    'directives.admin-domain-nav'
  ], ['$routeProvider', function($routeProvider){
  $routeProvider.when('/admin/domains', {
    templateUrl: 'partials/admin/domain.tpl.html',
    controller: 'AdminDomainController',
  });
}]);

angular.module('admin.domain').controller('AdminDomainController', [function () {
  
}]);
