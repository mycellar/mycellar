angular.module('mycellar.services.security.service', [
  'mycellar.services.menu'
]);

angular.module('mycellar.services.security.service').factory('security', [
  '$http', '$q', '$location', 'menuService',
  function($http, $q, $location, menuService) {
    // The public API of the service
    var service = {
      oldPath: '/',
      // Attempt to authenticate a user by the given email and password
      login: function(email, password) {
        return $http.post('/api/login', {email: email, password: password}).then(function(response) {
          service.currentUser = response.data;
          if (service.isAuthenticated()) {
            $location.path(service.oldPath);
          } else {
            $location.path('/');
          }
          menuService.reloadMenus();
        });
      },
  
      // Logout the current user and redirect
      logout: function() {
        $http.post('/api/logout').then(function() {
          service.currentUser = null;
          $location.path('/');
          menuService.reloadMenus();
        });
      },
  
      // Ask the backend to see if a user is already authenticated - this may be from a previous session.
      requestCurrentUser: function() {
        if ( service.isAuthenticated() ) {
          return $q.when(service.currentUser);
        } else {
          return $http.get('/api/current-user').then(function(response) {
            service.currentUser = response.data;
            return service.currentUser;
          });
        }
      },
  
      // Information about the current user
      currentUser: null,
  
      // Is the current user authenticated?
      isAuthenticated: function(){
        return !!service.currentUser && !!service.currentUser.email;
      },
      
      sendPasswordResetMail: function(email) {
        return $http.post('/api/sendPasswordResetMail', email);
      },
      
      resetPassword: function(key, password) {
        return $http.post('/api/resetPassword', {key: key, password: password});
      },
      
      getMailFromRequestKey: function(key) {
        return $http.get('/api/requestedMail?key='+key);
      }
      
    };
  
    return service;
  }
]);
