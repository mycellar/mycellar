'use strict';

angular.module('mycellar').controller({
  AdminDomainWinesController: function ($scope, $resource, $http) {
    $scope.wineResource = $resource('/api/domain/wine/wines/list');
    $http.get('/api/domain/wine/wines/count').success(function(data, status, headers, config) {
      $scope.wineCount = data;
      $scope.firstWine = 0;
      $scope.lastWine = $scope.wineCount < 20 ? $scope.wineCount : 20;
      $scope.wines = $scope.wineResource.query({first: $scope.firstWine, count: $scope.lastWine});  
    });
    $scope.gridOptions = { data: 'wines' };
  }
});