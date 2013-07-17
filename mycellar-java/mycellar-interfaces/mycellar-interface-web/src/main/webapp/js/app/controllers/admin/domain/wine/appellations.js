'use strict';

angular.module('mycellar').controller({
  AdminDomainAppellationsController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'region.country.name',
        'region.name',
        'name',
      ],
      ways: {
        'region.country.name': 'asc',
        'region.name': 'asc',
        'name': 'asc',
      }
    };
    $scope.filters = {
      'region.country.name': '',
      'region.name': '',
      name: ''
    };
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/appellations')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/appellation/' + itemId);
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
  AdminDomainAppellationController: function ($scope, $resource, $route, $location, $http) {
    var appellationId = $route.current.params.appellationId;
    $scope.appellationResource = $resource('/api/domain/wine/appellation/:appellationId');
    $scope.appellation = $scope.appellationResource.get({appellationId: appellationId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.appellation, $scope.backup);
      $scope.appellation.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/appellations/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.appellation);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.appellation);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/appellations/');
    };
    $scope.regions = function (regionName) {
      return $http.get("/api/domain/wine/regions?count=15&filters=name,"+regionName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
  }
});