angular.module('mycellar.directives.table.paginated', ['mycellar.services.table']);

angular.module('mycellar.directives.table.paginated').directive('paginated', ['$timeout', function($timeout) {
  return {
    restrict: 'E',
    transclude: true,
    replace: true,
    templateUrl: 'partials/directives/table/paginated.tpl.html',
    scope: {
      options: '=',
      tableContext: '=',
      items: '=',
    },
    link: function(scope, iElement, iAttrs, controller) {
      var started = false;
      scope.$watch('options.parameters', function(value) {
        if(started && scope.currentPage > 0) {
          scope.setPage(1);
        }
      }, true);
      scope.$watch('tableContext.sort.ways', function(value) {
        if (started && scope.currentPage > 0) {
          scope.setPage(scope.currentPage);
        }
      }, true);
      var timeoutId;
      scope.$watch('tableContext.filters', function(value) {
        if (started && scope.currentPage > 0) {
          if (timeoutId) {
            // cancel previous timeout
            $timeout.cancel(timeoutId);
          }
          timeoutId = $timeout(function () {
            scope.setPage(1);
          }, 500);
        }
      }, true);
      scope.$watch('itemsPerPage', function (value) {
        if (started && scope.currentPage >= 0) {
          scope.setPage(scope.currentPage);
        }
      });
      scope.$watch('result.count', function (value) {
        if (scope.result != undefined && scope.result.count != undefined) {
          scope.total = scope.result.count;
        }
      });
      scope.$watch('result.list', function (value) {
        if (scope.result != undefined && scope.result.list != undefined) {
          started = true;
          scope.items = scope.result.list;
          scope.count = scope.items.length;
        }
      }, true);
    },
    controller: function($scope) {
      $scope.setPage = function(page) {
        $scope.currentPage = page;
        $scope.firstItem = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var sort = [];
        for (var t in $scope.tableContext.sort.properties) {
          sort.push($scope.tableContext.sort.properties[t] + ',' + $scope.tableContext.sort.ways[$scope.tableContext.sort.properties[t]]);
        }
        var filters = [];
        for (var t in $scope.tableContext.filters) {
          filters.push(t + ',' + $scope.tableContext.filters[t]);
        }
        var parameters = angular.extend({}, $scope.options.parameters, {
          first: $scope.firstItem,
          count: $scope.itemsPerPage,
          sort: sort,
          filters: filters
        });
        $scope.result = $scope.itemResource(parameters);
      };
      
      $scope.pageRange = $scope.options.pageRange || 6;
      $scope.itemResource = $scope.options.itemResource;
      $scope.itemsPerPage = 10;
      $scope.total = 0;
      $scope.result = {
          list: [],
          count: $scope.itemsPerPage
      };
      if ($scope.options.defaultSort != null) {
        angular.forEach($scope.options.defaultSort, function(defaultSort) {
          $scope.tableContext.sortBy(defaultSort);
        });
      };
      $scope.setPage(1);
    }
  }
}]);
