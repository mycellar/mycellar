angular.module('mycellar.controllers.admin.domain.contact.contact', [
  'mycellar.resources.contact.contacts', 
  'mycellar.resources.wine.producers',
  'mycellar.services.admin',
  'mycellar.directives.form',
  'mycellar.directives.admin'
]);

angular.module('mycellar.controllers.admin.domain.contact.contact').controller('AdminDomainContactController', [
  '$scope', 'contact', 'adminDomainService',
  function ($scope, contact, adminDomainService) {
    $scope.contact = contact;
    
    angular.extend($scope, adminDomainService.editMethods('contact', 'Contact', contact, 'form'));
  }
]);
