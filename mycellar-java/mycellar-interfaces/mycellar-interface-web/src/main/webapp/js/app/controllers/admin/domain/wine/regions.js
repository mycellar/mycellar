'use strict';

angular.module('mycellar').controller({
  AdminDomainRegionsController: function ($scope, $resource, $http, $location, $route) {
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
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/regions')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/region/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/region/');
    };
    $scope.delete = function(itemId) {
      $resource('/api/domain/wine/region/:regionId').delete({regionId: itemId}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
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
  AdminDomainRegionController: function ($scope, $resource, $route, $location, $http) {
    var regionId = $route.current.params.regionId;
    $scope.regionResource = $resource('/api/domain/wine/region/:regionId');
    if (regionId != null && regionId > 0) {
      $scope.region = $scope.regionResource.get({regionId: regionId});
    } else {
      $scope.region = new $scope.regionResource();
    }
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.region, $scope.backup);
      $scope.region.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/regions/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.region);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.region);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/regions/');
    };
    $scope.countries = function (countryName) {
      return $http.get("/api/domain/wine/countries?count=15&filters=name,"+countryName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
  }
});