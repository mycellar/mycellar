angular.module('mycellar.resources.contact.contacts', ['ngResource']);

angular.module('mycellar.resources.contact.contacts').factory('Contacts', [
  '$resource', '$q', 
  function ($resource, $q) {

    var Contacts = $resource('/api/domain/contact/contacts/:id', {id: '@id'}, {
      getLastContacts: {
        url: '/api/contact/lastcontacts',
        method: 'GET'
      }
    });
    
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
    
    return Contacts;
  }
]);
