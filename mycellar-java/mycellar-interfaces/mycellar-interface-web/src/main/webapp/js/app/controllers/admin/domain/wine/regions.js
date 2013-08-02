'use strict';

angular.module('mycellar').controller({
  AdminDomainRegionsController: function ($scope, $resource, $http, $location, $route, regionService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: regionService.resource.list,
      defaultSort: ['country.name', 'name']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/region/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/region/');
    };
    $scope.delete = function(itemId) {
      regionService.resource.item.delete({regionId: itemId}, function (value, headers) {
        if (value.errorKey != undefined) {
          $scope.errors.push({errorKey: value.errorKey});
        } else if (value.internalError != undefined) {
          $scope.errors.push({errorKey: value.internalError});
        } else {
          $route.reload();
        }
      });
    };
  },
  AdminDomainRegionController: function ($scope, $route, $location, countryService, regionService) {
    var regionId = $route.current.params.regionId;
    if (regionId != null && regionId > 0) {
      $scope.region = regionService.resource.item.get({regionId: regionId});
    } else {
      $scope.region = new regionService.resource.item();
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
    $scope.countries = countryService.getAllLike;
  }
});