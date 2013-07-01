'use strict';

angular.module('mycellar').directive('mycellarTable', function() {
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
      sort: '='
    },
    link: function(scope, iElement, iAttrs, controller) {
      scope.$watch('sort.ways', function(value) {
        if (scope.currentPage >= 0) {
          scope.setPage(scope.currentPage);
        }
      }, true);
      scope.$watch('itemsPerPage', function (value) {
        if (scope.currentPage >= 0) {
          scope.setPage(scope.currentPage);
        }
      });
    },
    controller: function($scope) {
      $scope.setPage = function(page) {
        $scope.pageCount = (~~($scope.itemCount / $scope.itemsPerPage)) + 1;
        $scope.currentPage = page;
        $scope.pages = [];
        var i;
        if ($scope.currentPage < $scope.pageRange) {
          i = 0;
        } else if ($scope.currentPage >= $scope.pageCount - $scope.pageRange) {
          i = $scope.pageCount - $scope.pageRange * 2 - 1;
        } else {
          i = $scope.currentPage - $scope.pageRange;
        }
        for (; i < $scope.pageCount && $scope.pages.length < 2 * $scope.pageRange + 1 ; i++) {
          if (i >= 0) {
            $scope.pages.push({number: i});
          }
        }
        $scope.firstItem = $scope.currentPage * $scope.itemsPerPage;
        $scope.count = ($scope.currentPage + 1) * $scope.itemsPerPage > $scope.itemCount ? $scope.itemCount - $scope.currentPage * $scope.itemsPerPage : $scope.itemsPerPage * 1;
        var sort = [];
        for (var t in $scope.sort.properties) {
          sort.push($scope.sort.properties[t] + ',' + $scope.sort.ways[$scope.sort.properties[t]]);
        }
        $scope.items = $scope.itemResource.query({
          first: $scope.firstItem,
          count: $scope.count,
          sort: sort
        });
      };
      $scope.nextPage = function() {
        $scope.setPage($scope.currentPage + 1);
      };
      $scope.previousPage = function() {
        $scope.setPage($scope.currentPage - 1);
      };
      
      $scope.itemCountGet = $scope.options.itemCountGet;
      $scope.itemResource = $scope.options.itemResource;
      $scope.itemsPerPage = 20;
      
      $scope.itemCountGet.success(function(data, status, headers, config) {
        $scope.itemCount = data;
        $scope.setPage(0);
      });
    }
  }
});
