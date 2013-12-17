angular.module('mycellar.controllers.admin.domain.contact.contact', [
  'mycellar.resources.contact.contacts', 
  'mycellar.resources.wine.producers',
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.contact.contact').controller('AdminDomainContactController', [
  '$scope', 'contact', 'Producers', 'adminDomainService',
  function ($scope, contact, Producers, adminDomainService) {
    $scope.contact = contact;
    
    angular.extend($scope, adminDomainService.editMethods('contact', 'Contact', contact, 'form'));
    
    $scope.producers = Producers.nameLike;
  }
]);
