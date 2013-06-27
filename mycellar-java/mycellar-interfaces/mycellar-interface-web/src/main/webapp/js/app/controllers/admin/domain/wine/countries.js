'use strict';

angular.module('mycellar').controller({
  AdminDomainCountriesController: function ($scope, $resource, $http, $location) {
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/countries/list'),
      itemCountGet: $http.get('/api/domain/wine/countries/count'),
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/country/' + itemId);
    };
  },
  AdminDomainCountryController: function ($scope, $resource, $route, $location) {
    var countryId = $route.current.params.countryId;
    $scope.countryResource = $resource('/api/domain/wine/country/:countryId');
    $scope.country = $scope.countryResource.get({countryId: countryId});
    $scope.save = function () {
      $scope.country.$save();
      $location.path('/admin/domain/wine/countries/');
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/countries/');
    };
  }
});