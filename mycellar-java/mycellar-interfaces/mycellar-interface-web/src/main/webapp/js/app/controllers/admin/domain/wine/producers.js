'use strict';

angular.module('mycellar').controller({
  AdminDomainProducersController: function ($scope, $location, $route, producerService, tableService) {
    $scope.errors = [];
    
    $scope.tableOptions = {
      itemResource: producerService.resource.list,
      defaultSort: ['name']
    };
    $scope.tableContext = tableService.createTableContext();
      
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/producer/' + itemId);
    };
    $scope.new = function() {
      $location.path('/admin/domain/wine/producer/');
    };
    $scope.delete = function(itemId) {
      producerService.resource.item.delete({producerId: itemId}, function (value, headers) {
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
  AdminDomainProducerController: function ($scope, $route, $location, producerService) {
    var producerId = $route.current.params.producerId;
    if (producerId != null && producerId > 0) {
      $scope.producer = producerService.resource.item.get({producerId: producerId});
    } else {
      $scope.producer = new producerService.resource.item();
    }
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.producer, $scope.backup);
      $scope.producer.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/producers/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.producer);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.producer);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/producers/');
    };
  }
});