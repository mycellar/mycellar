angular.module('mycellar.directives.admin.domain-nav', [
  'mycellar.services.admin.domain'
]);

angular.module('mycellar.directives.admin.domain-nav').directive('adminDomainNav', [
  'adminDomainService', '$location', 
  function(adminDomainService, $location) {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/admin/domain-nav.tpl.html',
      controller: function($scope) {
        $scope.currentLocation = $location.path();
        $scope.menu = adminDomainService.getMenu();
      }
    }
  }
]);