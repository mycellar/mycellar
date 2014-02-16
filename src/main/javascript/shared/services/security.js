angular.module('mycellar.services.security', [
  'mycellar.services.menu',
  'ngCookies'
]);

angular.module('mycellar.services.security').factory('security', [
  '$http', '$q', '$cookieStore', '$rootScope', '$location', 'menuService',
  function($http, $q, $cookieStore, $rootScope, $location, menuService) {
    // The public API of the service
    var service = {
      // Attempt to authenticate a user by the given email and password
      login: function(email, password) {
        return $http.post('/api/login', {email: email, password: password}, {ignoreAuthModule: true}).success(function(data, status, headers) {
          $rootScope.currentUser = data;
          setToken(headers(tokenHeaderName));
          menuService.reloadMenus();
        });
      },
  
      // Attempt to logout the current user
      logout: function() {
        $http.post('/api/logout').then(function() {
          service.forceLogout();
          $location.path('/');
          menuService.reloadMenus();
        });
      },
      
      // Forget the current user
      forceLogout: function() {
        $rootScope.currentUser = null;
        deleteToken();
      },
      
      register: function(user) {
        return $http.post('/api/register', user).success(function(data, status, headers) {
          $rootScope.currentUser = data;
          setToken(headers(tokenHeaderName));
          menuService.reloadMenus();
        });
      },
  
      // Ask the backend to see if a user is already authenticated - this may be from a previous session.
      requestCurrentUser: function() {
        if ( service.isAuthenticated() ) {
          return $q.when($rootScope.currentUser);
        } else {
          $http.defaults.headers.common['Rest-Token'] = getToken();
          return $http.get('/api/current-user').then(function(response) {
            $rootScope.currentUser = response.data;
            menuService.reloadMenus();
            return $rootScope.currentUser;
          });
        }
      },
  
      // Is the current user authenticated?
      isAuthenticated: function(){
        return !!$rootScope.currentUser && !!$rootScope.currentUser.email;
      },
      
      sendPasswordResetMail: function(email) {
        return $http.post('/api/sendPasswordResetMail', email);
      },
      
      resetPassword: function(key, password) {
        return $http.post('/api/resetPassword', {key: key, password: password});
      },
      
      getMailFromRequestKey: function(key) {
        return $http.get('/api/requestedMail?key='+key);
      },
      
      changeEmail: function(email, password) {
        return $http.post('/api/changeEmail', {email: email, password: password}).success(function(data, status, headers) {
          $rootScope.currentUser = data;
          setToken(headers(tokenHeaderName));
        }); 
      },
      
      changePassword: function(oldPassword, password) {
        return $http.post('/api/changePassword', {oldPassword: oldPassword, password: password}).success(function(data, status, headers) {
          $rootScope.currentUser = data;
          setToken(headers(tokenHeaderName));
        });
      },

      updateHeader: function(config) {
        config.headers[tokenHeaderName] = getToken();
        return config;
      }
      
    };
    // private API
    var tokenHeaderName = 'Rest-Token';
    var setToken = function(token) {
      $http.defaults.headers.common[tokenHeaderName] = token;
      if (window.localStorage) {
        window.localStorage.setItem(tokenHeaderName, token);
      } else {
        $cookieStore.put(tokenHeaderName, token);
      }
    };
    var getToken = function() {
      if (window.localStorage) {
        return window.localStorage.getItem(tokenHeaderName);
      } else {
        return $cookieStore.get(tokenHeaderName);
      }
    };
    var deleteToken = function() {
      delete $http.defaults.headers.common[tokenHeaderName];
      if (window.localStorage) {
        window.localStorage.removeItem(tokenHeaderName);
      } else {
        $cookieStore.remove(tokenHeaderName);
      }
    };
    return service;
  }
]);
