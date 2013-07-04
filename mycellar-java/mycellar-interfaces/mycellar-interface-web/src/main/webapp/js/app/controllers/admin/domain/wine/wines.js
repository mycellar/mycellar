'use strict';

angular.module('mycellar').controller({
  AdminDomainWinesController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'appellation.region.country.name',
        'appellation.region.name', 
        'appellation.name', 
        'producer.name',
        'name',
        'vintage'
      ],
      ways: {
        'appellation.region.country.name': 'asc',
        'appellation.region.name': 'asc',
        'appellation.name': 'asc',
        'producer.name': 'asc',
        'name': 'asc',
        'vintage': 'asc',
        'color': null,
        'type': null
      }
    };
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/wines'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/wine/' + itemId);
    };
    $scope.sortBy = function(property) {
      if ($scope.sort.ways[property] == 'asc') {
        $scope.sort.ways[property] = 'desc';
      } else if ($scope.sort.ways[property] == 'desc') {
        $scope.sort.properties.splice($scope.sort.properties.indexOf(property), 1);
        $scope.sort.ways[property] = null;
      } else {
        $scope.sort.properties.push(property);
        $scope.sort.ways[property] = 'asc';
      }
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