'use strict';

angular.module('mycellar').controller({
  AdminDomainCountriesController: function ($scope, $location, $route, countryService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: countryService.resource.list,
      defaultSort: ['name']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/country/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/country/');
    };
    $scope.delete = function(itemId) {
      countryService.resource.item.delete({countryId: itemId}, function (value, headers) {
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
  AdminDomainCountryController: function ($scope, $route, $location, countryService) {
    var countryId = $route.current.params.countryId;
    if (countryId != null && countryId > 0) {
      $scope.country = countryService.resource.item.get({countryId: countryId});
    } else {
      $scope.country = new countryService.resource.item();
    }
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
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.country);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/countries/');
    };
  }
});