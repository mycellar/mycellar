'use strict';

angular.module('mycellar').directive('mycellarTable', function() {
  return {
    restrict: 'E',
    transclude: true,
    replace: true,
    templateUrl: 'partials/directives/table.html',
    scope: {
      options: '=',
      itemsPerPage: '=',
      pageRange: '=',
      items: '=',
      colSpan: '=',
    },
    controller: function($scope) {
      $scope.setPage = function(page) {
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
        $scope.count = ($scope.currentPage + 1) * $scope.itemsPerPage > $scope.itemCount ? $scope.itemCount - $scope.currentPage * $scope.itemsPerPage : $scope.itemsPerPage;
        $scope.items = $scope.itemResource.query({
          first: $scope.firstItem, 
          count: $scope.count
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
      
      $scope.itemCountGet.success(function(data, status, headers, config) {
        $scope.itemCount = data;
        $scope.pageCount = (~~($scope.itemCount / $scope.itemsPerPage)) + 1;
        $scope.setPage(0);
      });
    }
  }
});
