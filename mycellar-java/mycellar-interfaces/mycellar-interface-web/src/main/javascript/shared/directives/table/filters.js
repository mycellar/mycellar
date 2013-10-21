angular.module('directives.table.filters', ['mycellar.services.table']);

angular.module('directives.table.filters').directive('tableFilters', [function() {
  return {
    restrict: 'E',
    replace: true,
    transclude: true,
    templateUrl: 'partials/directives/table/tableFilters.tpl.html',
    scope: {
      tableContext: '='
    }
  }
}]);
