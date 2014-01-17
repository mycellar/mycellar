angular.module('mycellar.controllers.admin.domain.wine.appellation', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.appellation').controller('AdminDomainAppellationController', [
  '$scope', 'appellation', 'adminDomainService',
  function ($scope, appellation, adminDomainService) {
    $scope.appellation = appellation;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Appellation', appellation, 'form'));
  }
]);
