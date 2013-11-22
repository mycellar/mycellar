angular.module('mycellar.controllers.admin.page', [
  'ngRoute',
  'mycellar.resources.wine.wines', 
  'mycellar.resources.user.users', 
  'mycellar.resources.stack.stacks'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin', {
      templateUrl: 'partials/admin/admin.tpl.html',
      controller: 'AdminController',
      resolve: {
        winesCount: ['Wines', function(Wines){
          return Wines.count();
        }],
        usersCount: ['Users', function(Users) {
          return Users.count();
        }],
        stacksCount: ['Stacks', function(Stacks) {
          return Stacks.count();
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.admin.page').controller('AdminController', [
  '$scope', 'winesCount', 'usersCount', 'stacksCount', 
  function ($scope, winesCount, usersCount, stacksCount) {
    $scope.angularVersion = angular.version.full;
    $scope.mycellarVersion = mycellar.version.full;
    $scope.stacksCount = stacksCount;
    $scope.usersCount = usersCount;
    $scope.winesCount = winesCount;
  }
]);