angular.module('admin.domain.wine.producer', [
  'resources.wine.producers',
  'services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.wine.producer').controller('AdminDomainProducerController', [
  '$scope', 'producer', 'adminDomainService',
  function ($scope, producer, adminDomainService) {
    $scope.producer = producer;
    angular.extend($scope, adminDomainService.editMethods('wine', 'Producer', producer, 'form'));
  }
]);
