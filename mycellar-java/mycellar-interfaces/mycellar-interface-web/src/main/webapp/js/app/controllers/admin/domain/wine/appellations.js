'use strict';

angular.module('mycellar').controller({
  AdminDomainAppellationsController: function ($scope, $location, $route, appellationService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: appellationService.resource.list,
      defaultSort: ['region.country.name', 'region.name', 'name']
    };
    $scope.tableContext = tableService.createTableContext();
      
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/appellation/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/appellation/');
    };
    $scope.delete = function(itemId) {
      appellationService.resource.item.delete({appellationId: itemId}, function (value, headers) {
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
  AdminDomainAppellationController: function ($scope, $route, $location, appellationService, regionService) {
    var appellationId = $route.current.params.appellationId;
    if (appellationId != null && appellationId > 0) {
      $scope.appellation = appellationService.resource.item.get({appellationId: appellationId});
    } else {
      $scope.appellation = new appellationService.resource.item();
    }
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
    $scope.regions = regionService.getAllLike;
  }
});