'use strict';

angular.module('mycellar').controller({
  AdminDomainConfigurationsController: function ($scope, $location, configurationService, tableService) {
    $scope.tableOptions = {
      itemResource: configurationService.resource.list,
      defaultSort: ['key']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/admin/configuration/' + itemId);
    };
  },
  AdminDomainConfigurationController: function ($scope, $resource, $route, $location) {
    var configurationId = $route.current.params.configurationId;
    $scope.configurationResource = $resource('/api/domain/admin/configuration/:configurationId');
    $scope.configuration = $scope.configurationResource.get({configurationId: configurationId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.configuration, $scope.backup);
      $scope.configuration.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/admin/configurations/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.configuration);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.configuration);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/admin/configurations/');
    };
  }
});