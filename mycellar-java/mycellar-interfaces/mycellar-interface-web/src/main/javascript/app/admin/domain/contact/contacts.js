angular.module('admin.domain.contact.contacts', [
  'admin.domain.contact.contact', 
  'resources.contact.contacts', 
  'directives.table', 
  'directives.error',
  'directives.admin-domain-nav',
  'services.admin-domain'
], [
  'adminDomainServiceProvider', 
  function(adminDomainServiceProvider){
    adminDomainServiceProvider.forDomain('contact', 'Contact', 'Contacts', 'contact', 'Contacts')
      .whenCrud({}, {
        contact: ['$route', 'Contacts', function ($route, Contacts) {
          var id = $route.current.params.id;
          if (id != null && id > 0) {
            return Contacts.getById(id);
          } else {
            return Contacts.new();
          }
        }]
      }
    );
  }
]);

angular.module('admin.domain.contact.contacts').controller('AdminDomainContactsController', [
  '$scope', 'Contacts', 'adminDomainService',
  function ($scope, Contacts, adminDomainService) {
    angular.extend($scope, adminDomainService.listMethods('contact', 'Contact', Contacts, ['current', 'current', 'producer.name']));
  }
]);
