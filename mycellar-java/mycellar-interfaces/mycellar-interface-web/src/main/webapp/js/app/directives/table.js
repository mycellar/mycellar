'use strict';

angular.module('mycellar').directive({
  mycellarTable: function($timeout) {
    return {
      restrict: 'E',
      transclude: true,
      replace: true,
      templateUrl: 'partials/directives/table.html',
      scope: {
        options: '=',
        pageRange: '=',
        items: '=',
        colSpan: '=',
        sort: '=',
        filters: '='
      },
      link: function(scope, iElement, iAttrs, controller) {
        var started = false;
        scope.$watch('sort.ways', function(value) {
          if (started && scope.currentPage >= 0) {
            scope.setPage(scope.currentPage);
          }
        }, true);
        var timeoutId;
        scope.$watch('filters', function(value) {
          if (started && scope.currentPage >= 0) {
            if (timeoutId) {
              $timeout.cancel(timeoutId);//cancel previous timeout
            }
            timeoutId = $timeout(function () {
              scope.setPage(0);
            }, 500);
          }
        }, true);
        scope.$watch('itemsPerPage', function (value) {
          if (started && scope.currentPage >= 0) {
            scope.setPage(scope.currentPage);
          }
        });
        scope.$watch('result.count', function (value) {
          scope.pageCount = (~~(scope.result.count / scope.itemsPerPage)) + 1;
          scope.pages = [];
          var i;
          if (scope.currentPage < scope.pageRange) {
            i = 0;
          } else if (scope.currentPage >= scope.pageCount - scope.pageRange) {
            i = scope.pageCount - scope.pageRange * 2 - 1;
          } else {
            i = scope.currentPage - scope.pageRange;
          }
          for (; i < scope.pageCount && scope.pages.length < 2 * scope.pageRange + 1 ; i++) {
            if (i >= 0) {
              scope.pages.push({number: i});
            }
          }
        });
        scope.$watch('result.list', function (value) {
          if (scope.result.list != undefined) {
            started = true;
            scope.items = scope.result.list;
            scope.count = scope.items.length;
          }
        }, true);
      },
      controller: function($scope) {
        $scope.setPage = function(page) {
          $scope.currentPage = page;
          $scope.firstItem = $scope.currentPage * $scope.itemsPerPage;
          var sort = [];
          for (var t in $scope.sort.properties) {
            sort.push($scope.sort.properties[t] + ',' + $scope.sort.ways[$scope.sort.properties[t]]);
          }
          var filters = [];
          for (var t in $scope.filters) {
            filters.push(t + ',' + $scope.filters[t]);
          }
          $scope.result = $scope.itemResource.get({
            first: $scope.firstItem,
            count: $scope.itemsPerPage,
            sort: sort,
            filters: filters
          });
        };
        $scope.nextPage = function() {
          $scope.setPage($scope.currentPage + 1);
        };
        $scope.previousPage = function() {
          $scope.setPage($scope.currentPage - 1);
        };
        
        $scope.itemResource = $scope.options.itemResource;
        $scope.itemsPerPage = 10;
        $scope.result = {
            list: [],
            count: $scope.itemsPerPage
        };
        $scope.setPage(0);
      }
    }
  },
  'iconSort': function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/icon-sort.html',
      scope: {
        ways: '=',
        sortFn: '&',
        attribute: '@'
      }
    }
  }
});
