'use strict';

angular.module('mycellar', ['http-auth-interceptor', 'ngResource'], function($locationProvider) {
  $locationProvider.html5Mode(true);
});