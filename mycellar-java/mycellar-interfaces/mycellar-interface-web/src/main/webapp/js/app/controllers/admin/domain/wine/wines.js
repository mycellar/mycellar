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
    $scope.filtersIsCollapsed = true;
    
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
    $scope.clearFilters = function() {
      for (var filter in $scope.filters) {
        $scope.filters[filter] = '';
      }
    };
  },
  AdminDomainWineController: function ($scope, $resource, $route, $location) {
    var wineId = $route.current.params.wineId;
    $scope.wineResource = $resource('/api/domain/wine/wine/:wineId');
    $scope.wine = $scope.wineResource.get({wineId: wineId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.wine, $scope.backup);
      $scope.wine.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/wines/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.wine);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/wines/');
    };
  }
});