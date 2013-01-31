'use strict';

angular.module('mycellar', ['http-auth-interceptor', 'ngResource', 'bootstrap'], function($locationProvider) {
  $locationProvider.html5Mode(true);
});