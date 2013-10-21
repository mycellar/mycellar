angular.module('mycellar.controllers.admin.domain.wine.producer', [
  'mycellar.resources.wine.producers',
  'mycellar.services.admin-domain',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.wine.producer').controller('AdminDomainProducerController', [
  '$scope', 'producer', 'adminDomainService',
  function ($scope, producer, adminDomainService) {
    $scope.producer = producer;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Producer', producer, 'form'));
  }
]);
