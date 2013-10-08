angular.module('admin.domain.contact.contact', [
  'resources.contact.contacts', 
  'resources.wine.producers',
  'services.admin-domain',
  'directives.admin-domain-nav'
]);

angular.module('admin.domain.contact.contact').controller('AdminDomainContactController', [
  '$scope', 'contact', 'Producers', 'adminDomainService',
  function ($scope, contact, Producers, adminDomainService) {
    $scope.contact = contact;
    
    angular.extend($scope, adminDomainService.editMethods('contact', 'Contact', contact, 'form'));
    
    $scope.producers = Producers.nameLike;
  }
]);
