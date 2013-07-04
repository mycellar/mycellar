'use strict';

angular.module('mycellar').controller({
  AdminDomainRegionsController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'country.name',
        'name',
      ],
      ways: {
        'country.name': 'asc',
        'name': 'asc',
      }
    };
    $scope.filters = {
      'country.name': '',
      name: ''
    };
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/regions')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/region/' + itemId);
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
    $scope.clearFilters = function() {
      for (var filter in $scope.filters) {
        $scope.filters[filter] = '';
      }
    };
  },
  AdminDomainRegionController: function ($scope, $resource, $route, $location) {
    var regionId = $route.current.params.regionId;
    $scope.regionResource = $resource('/api/domain/wine/region/:regionId');
    $scope.region = $scope.regionResource.get({regionId: regionId});
    $scope.save = function () {
      $scope.region.$save();
      $location.path('/admin/domain/wine/regions/');
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/regions/');
    };
  }
});