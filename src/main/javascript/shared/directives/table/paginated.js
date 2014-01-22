angular.module('mycellar.directives.table.paginated', ['mycellar.services.table']);

angular.module('mycellar.directives.table.paginated').directive('paginated', [
  function() {
    return {
      restrict: 'E',
      transclude: true,
      replace: true,
      templateUrl: 'partials/directives/table/paginated.tpl.html',
      scope: {
        tableContext: '='
      },
      controller: function($scope) {
        $scope.$watch('tableContext.result.count', function (value) {
          if (value != undefined) {
            $scope.tableContext.total = value;
          }
        });
        $scope.$watch('tableContext.result.list', function (value) {
          if (value != undefined) {
            $scope.tableContext.items = value;
            $scope.tableContext.count = value.length;
          }
        }, true);
        $scope.$watch('tableContext.parameters', function(value, oldValue) {
          if(value != undefined && oldValue !== value) {
            $scope.tableContext.setPage(1);
          }
        }, true);
        $scope.$watch('tableContext.itemsPerPage', function (value, oldValue) {
          if (value != undefined && oldValue !== value) {
            $scope.tableContext.setPage(1);
          }
        });
        $scope.$watch('tableContext.sort.ways', function(value, oldValue) {
          if (value != undefined && oldValue !== value) {
            $scope.tableContext.setPage(1);
          }
        }, true);
        var timeoutId;
        $scope.$watch('tableContext.filters', function(value, oldValue) {
          if (value != undefined && oldValue !== value) {
            if (timeoutId) {
              // cancel previous timeout
              $timeout.cancel(timeoutId);
            }
            timeoutId = $timeout(function () {
              $scope.tableContext.setPage(1);
            }, 500);
          }
        }, true);
      }
    }
  }
]);
