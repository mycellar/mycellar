angular.module('mycellar.controllers.admin.domain.wine.producer', [
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.producer').controller('AdminDomainProducerController', [
  '$scope', 'producer', 'adminDomainService',
  function ($scope, producer, adminDomainService) {
    $scope.producer = producer;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Producer', producer, 'form'));
  }
]);
