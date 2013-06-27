'use strict';

angular.module('mycellar').controller({
  AdminDomainWinesController: function ($scope, $resource, $http, $location) {
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/wines/list'),
      itemCountGet: $http.get('/api/domain/wine/wines/count'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/wine/' + itemId);
    };
  },
  AdminDomainWineController: function ($scope, $resource, $route, $location) {
    var wineId = $route.current.params.wineId;
    $scope.wineResource = $resource('/api/domain/wine/wine/:wineId');
    $scope.wine = $scope.wineResource.get({wineId: wineId});
    $scope.save = function () {
      $scope.country.$save();
      $location.path('/admin/domain/wine/wines/');
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/wines/');
    };
  }
});