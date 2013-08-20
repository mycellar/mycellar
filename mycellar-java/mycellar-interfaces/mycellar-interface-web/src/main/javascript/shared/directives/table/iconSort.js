angular.module('directives.table.iconSort', ['services.table']);

angular.module('directives.table.iconSort').directive('iconSort', [function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/directives/table/icon-sort.tpl.html',
    scope: {
      tableContext: '=',
      attribute: '@'
    },
    controller: function($scope) {
      $scope.sortFn = function() {
        $scope.tableContext.sortBy($scope.attribute);
      };
    }
  }
}]);
