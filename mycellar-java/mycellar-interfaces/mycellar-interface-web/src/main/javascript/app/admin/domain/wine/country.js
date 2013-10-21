angular.module('admin.domain.wine.country', [
  'resources.wine.countries',
  'mycellar.services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.wine.country').controller('AdminDomainCountryController', [
  '$scope', 'country', 'adminDomainService',
  function ($scope, country, adminDomainService, Countries) {
    $scope.country = country;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Country', country, 'form'));
  }
]);
