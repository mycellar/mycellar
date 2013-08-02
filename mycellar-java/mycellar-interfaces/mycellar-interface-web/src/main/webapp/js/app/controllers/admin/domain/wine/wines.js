'use strict';

angular.module('mycellar').controller({
  AdminDomainWinesController: function ($scope, $location, $route, wineService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: wineService.resource.list,
      defaultSort: ['appellation.region.country.name',
                    'appellation.region.name',
                    'appellation.name',
                    'producer.name',
                    'name', 
                    'vintage']
    };
    $scope.tableContext = tableService.createTableContext();
  
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/wine/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/country/');
    };
    $scope.delete = function(itemId) {
      wineService.resource.item.delete({wineId: itemId}, function (value, headers) {
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
  AdminDomainWineController: function ($scope, $route, $location, wineService, appellationService, producerService) {
    var wineId = $route.current.params.wineId;
    if (wineId != null && wineId > 0) {
      $scope.wine = wineService.resource.item.get({wineId: wineId});
    } else {
      $scope.wine = new wineService.resource.item();
    }
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
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.wine);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/wines/');
    };
    $scope.appellations = appellationService.getAllLike;
    $scope.producers = producerService.getAllLike;
  }
});