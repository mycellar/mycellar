angular.module('mycellar.resources.contact.contacts', [
  'mycellar.services.resource'
]);

angular.module('mycellar.resources.contact.contacts').factory('Contacts', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/contact/contacts'
    }, {
      getLastContacts: {
        url: '/api/contact/lastcontacts',
        method: 'GET'
      }
    });
  }
]);

angular.module('mycellar.resources.contact.contacts').factory('AdminContacts', [
  'domainResource',
  function (domainResource) {
    return domainResource.createResource({
      url: '/api/admin/domain/contact/contacts'
    });
  }
]);
