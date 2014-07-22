angular.module('mycellar.services.login', [
  
]);

angular.module('mycellar.services.login').factory('loginDialogService', [
  '$rootScope', 'authService', '$location', 'security', '$q',
  function($rootScope, authService, $location, security, $q) {
    // The public API of the service
    var service = {
      create: function() {
        if ($rootScope.loginDialog == null) {
          $rootScope.loginDialog = document.querySelector("#loginDialog");
          $rootScope.loginDialog.result = $q.defer();
          $rootScope.loginDialog.result.promise.then(function() {
            authService.loginConfirmed(null, security.updateHeader);
          }, function(reason) {
            authService.loginCancelled(null, reason.reason);
            if (reason.requestPassword) {
              $location.path('/reset-password-request');
            } else {
              $location.path('/');
            }
          });
        }
      },
      destroy: function() {
        $rootScope.loginDialog = null;
      },
      loginFailure: function() {
        $rootScope.loginDialog.querySelector('#email').$.input.setCustomValidity('Vos identifiants ne sont pas reconnus.');
        $rootScope.loginDialog.querySelector('#password').$.input.setCustomValidity('Vos identifiants ne sont pas reconnus.');
        var toggleFunction = function(e) {
          $rootScope.loginDialog.removeEventListener('core-overlay-open', toggleFunction);
          $rootScope.loginDialog.toggle();
        };
        $rootScope.loginDialog.addEventListener('core-overlay-open', toggleFunction); 
      },
      loginSuccess: function() {
        $rootScope.loginDialog.result.resolve();
      },
      cancel: function() {
        $rootScope.loginDialog.result.reject({reason: 'Login cancelled by user.'});
      },
      forgotPassword: function() {
        $rootScope.loginDialog.result.reject({reason: 'Forgot password', requestPassword: true});
      }
    };
    return service;
  }
]);
