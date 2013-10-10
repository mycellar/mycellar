angular.module('resources.contact.contacts', ['ngResource']);

angular.module('resources.contact.contacts').factory('Contacts', [
  '$resource', '$q', 
  function ($resource, $q) {

    var Contacts = $resource('/api/domain/contact/contacts', {}, {
      getLastContacts: {
        url: '/api/contact/lastcontacts',
        method: 'GET'
      }
    });
    var Contact = $resource('/api/domain/contact/contact/:contactId');
    
    Contacts.deleteById = function(id, fn) {
      return Contact.delete({contactId: id}, fn);
    };
    
    Contacts.count = function () {
      var deferred = $q.defer();
      Contacts.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };
    
    Contacts.getById = function(id) {
      return Contact.get({contactId: id});
    };
    
    Contacts.new = function() {
      return new Contact();
    };

    return Contacts;
  }
]);
