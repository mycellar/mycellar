angular.module('mycellar.controllers.admin.domain.wine.country', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.country').controller('AdminDomainCountryController', [
  '$scope', 'country', 'adminDomainService',
  function ($scope, country, adminDomainService) {
    $scope.country = country;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Country', country, 'form'));
  }
]);
