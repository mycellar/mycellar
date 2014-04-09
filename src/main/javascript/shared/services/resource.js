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
     *   validateUrl: the resource validation url,
     *   likeUrl: the resource like url
     * }
     */
    domainResource.createResource = function(parameters, actions) {
      actions = actions || {};
      if (parameters.validateUrl != undefined) {
        actions.validate = {
          url: parameters.validateUrl,
          method: 'POST'
        }
      }
      if (parameters.likeUrl != undefined) {
        actions._like = {
          url: parameters.likeUrl,
          method: 'GET'
        }
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
      if (parameters.likeUrl != undefined) {
        resource.like = function(term) {
          var deferred = $q.defer();
          resource._like({
            input: term,
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
      }
      return resource;
    };
    return domainResource;
  }
]);
