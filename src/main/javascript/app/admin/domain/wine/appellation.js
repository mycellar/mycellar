angular.module('mycellar.controllers.admin.domain.wine.appellation', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService', 'Regions', 'Countries',
  function ($scope, appellation, adminDomainService, Regions, Countries) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));
  }
]);
