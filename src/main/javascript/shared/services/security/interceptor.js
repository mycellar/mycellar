angular.module('mycellar.services.security.interceptor', ['mycellar.services.security.service']);

angular.module('mycellar.services.security.interceptor').factory('securityInterceptor', [
  '$injector', 
  function($injector) {
    return function(promise) {
      // Intercept failed requests
      return promise.then(function(originalResponse) {
        if(originalResponse.status === 401) {
          $injector.get('security').oldPath = $injector.get('$location').path();
          $injector.get('$location').path("/login");
        }
        return promise;
      });
    };
  }
]);

// We have to add the interceptor to the queue as a string because the interceptor depends upon service instances that are not available in the config block.
angular.module('mycellar.services.security.interceptor').config(['$httpProvider', function($httpProvider) {
  $httpProvider.responseInterceptors.push('securityInterceptor');
}]);