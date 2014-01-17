angular.module('mycellar.directives.admin.domain', [
  'mycellar.directives.admin.domain-nav'
]);

angular.module('mycellar.directives.admin.domain').directive('adminDomain', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/admin/domain.tpl.html'
    }
  }
]);
