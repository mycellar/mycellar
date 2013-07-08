'use strict';

angular.module('mycellar').controller({
  AdminDomainCountriesController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'name',
      ],
      ways: {
        name: 'asc',
      }
    };
    $scope.filters = {
      name: ''
    }
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/countries')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/country/' + itemId);
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
  AdminDomainCountryController: function ($scope, $resource, $route, $location) {
    var countryId = $route.current.params.countryId;
    $scope.countryResource = $resource('/api/domain/wine/country/:countryId');
    $scope.country = $scope.countryResource.get({countryId: countryId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.country, $scope.backup);
      $scope.country.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/countries/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.country);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/countries/');
    };
  }
});