angular.module('mycellar.controllers.admin.domain.wine.country', [
  'mycellar.resources.wine.countries',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.country').controller('AdminDomainCountryController', [
  '$scope', 'country', 'adminDomainService',
  function ($scope, country, adminDomainService, Countries) {
    $scope.country = country;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Country', country, 'form'));
  }
]);
