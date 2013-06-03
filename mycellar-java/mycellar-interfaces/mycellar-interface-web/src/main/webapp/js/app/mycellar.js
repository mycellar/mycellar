'use strict';

var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.6.3-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', ['ngGrid', 'ui', 'http-auth-interceptor', 'ngResource', 'bootstrap'], function($locationProvider) {
  $locationProvider.html5Mode(true);
}).config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeController'});
  $routeProvider.when('/admin', {templateUrl: 'partials/admin/admin.html', controller: 'AdminController'});
  $routeProvider.when('/admin/lists', {templateUrl: 'partials/admin/lists.html', controller: 'AdminListsController'});
  $routeProvider.when('/admin/domain/wine/wines', {templateUrl: 'partials/admin/domain/wine/wines.html', controller: 'AdminDomainWinesController'});
  $routeProvider.when('/admin/domain/stack/stacks', {templateUrl: 'partials/admin/domain/stack/stacks.html', controller: 'AdminDomainStacksController'});
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
