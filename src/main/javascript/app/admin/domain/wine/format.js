angular.module('mycellar.controllers.admin.domain.wine.format', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.format').controller('AdminDomainFormatController', [
  '$scope', 'format', 'adminDomainService',
  function ($scope, format, adminDomainService) {
    $scope.format = format;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Format', format, 'form'));
  }
]);
