angular.module('mycellar.controllers.admin.domain.wine.producer', [
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.wine.producer').controller('AdminDomainProducerController', [
  '$scope', 'producer', 'adminDomainService',
  function ($scope, producer, adminDomainService) {
    $scope.producer = producer;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Producer', producer, 'form'));
  }
]);
