angular.module('mycellar.services.resource', [
  'ngResource'
]);

angular.module('mycellar.services.resource').factory('domainResource', [
  '$resource', '$q',
  function($resource, $q) {
    var domainResource = {};
    /**
     * parameters = {
     *   url: the resource url,
     *   validateUrl: the resource validation url
     * }
     */
    domainResource.createResource = function(parameters, actions) {
      actions = actions || {};
      if (parameters.validateUrl != undefined) {
        actions.validate = {
          url: parameters.validateUrl,
          method: 'POST'
        };
      }
      var resource = $resource(parameters.url+'/:id', {id: '@id'}, actions);
      resource.count = function() {
        var deferred = $q.defer();
        resource.get({count: 0}, function(result) {
          $q.when(result.count).then(function(value) {
            deferred.resolve(value);
          }, function(value) {
            deferred.reject(value);
          });
        });
        return deferred.promise;
      };
      resource.like = function(term) {
        var deferred = $q.defer();
        resource.get({
          like: term,
          first: 0,
          count: 20
        }, function(result) {
          $q.when(result.list).then(function(value) {
            deferred.resolve(value);
          }, function(value) {
            deferred.reject(value);
          });
        });
        return deferred.promise;
      };
      return resource;
    };
    return domainResource;
  }
]);
