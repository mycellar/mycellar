'use strict';

var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.6.3-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', ['loading', 'ui', 'ui.bootstrap', 'http-auth-interceptor', 'ngResource'], function($locationProvider) {
  $locationProvider.html5Mode(true);
}).config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeController'});
  $routeProvider.when('/admin', {templateUrl: 'partials/admin/admin.html', controller: 'AdminController'});
  $routeProvider.when('/admin/lists', {templateUrl: 'partials/admin/lists.html', controller: 'AdminListsController'});
  $routeProvider.when('/admin/domain/booking/bookings', {templateUrl: 'partials/admin/domain/booking/bookings.html', controller: 'AdminDomainBookingsController'});
  $routeProvider.when('/admin/domain/booking/booking/:bookingId', {templateUrl: 'partials/admin/domain/booking/booking.html', controller: 'AdminDomainBookingController'});
  $routeProvider.when('/admin/domain/booking/bookingEvents', {templateUrl: 'partials/admin/domain/booking/bookingEvents.html', controller: 'AdminDomainBookingEventsController'});
  $routeProvider.when('/admin/domain/booking/bookingEvent/:bookingEventId', {templateUrl: 'partials/admin/domain/booking/bookingEvent.html', controller: 'AdminDomainBookingEventController'});
  $routeProvider.when('/admin/domain/stack/stacks', {templateUrl: 'partials/admin/domain/stack/stacks.html', controller: 'AdminDomainStacksController'});
  $routeProvider.when('/admin/domain/stack/stack/:stackId', {templateUrl: 'partials/admin/domain/stack/stack.html', controller: 'AdminDomainStackController'});
  $routeProvider.when('/admin/domain/user/users', {templateUrl: 'partials/admin/domain/user/users.html', controller: 'AdminDomainUsersController'});
  $routeProvider.when('/admin/domain/user/user/:userId', {templateUrl: 'partials/admin/domain/user/user.html', controller: 'AdminDomainUserController'});
  $routeProvider.when('/admin/domain/wine/appellations', {templateUrl: 'partials/admin/domain/wine/appellations.html', controller: 'AdminDomainAppellationsController'});
  $routeProvider.when('/admin/domain/wine/appellation/:countryId', {templateUrl: 'partials/admin/domain/wine/appellation.html', controller: 'AdminDomainAppellationController'});
  $routeProvider.when('/admin/domain/wine/countries', {templateUrl: 'partials/admin/domain/wine/countries.html', controller: 'AdminDomainCountriesController'});
  $routeProvider.when('/admin/domain/wine/country/:countryId', {templateUrl: 'partials/admin/domain/wine/country.html', controller: 'AdminDomainCountryController'});
  $routeProvider.when('/admin/domain/wine/regions', {templateUrl: 'partials/admin/domain/wine/regions.html', controller: 'AdminDomainRegionsController'});
  $routeProvider.when('/admin/domain/wine/region/:regionId', {templateUrl: 'partials/admin/domain/wine/region.html', controller: 'AdminDomainRegionController'});
  $routeProvider.when('/admin/domain/wine/wines', {templateUrl: 'partials/admin/domain/wine/wines.html', controller: 'AdminDomainWinesController'});
  $routeProvider.when('/admin/domain/wine/wine/:wineId', {templateUrl: 'partials/admin/domain/wine/wine.html', controller: 'AdminDomainWineController'});
  $routeProvider.otherwise({redirectTo: '/home'});
}]);