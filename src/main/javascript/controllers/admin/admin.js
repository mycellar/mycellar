angular.module('mycellar.controllers.admin.page', [
  'ngRoute',
  'mycellar.resources.wine.wines', 
  'mycellar.resources.user.users', 
  'mycellar.resources.stack.stacks',
  'mycellar.resources.admin.administration'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin', {
      templateUrl: 'partials/views/admin/admin.tpl.html',
      controller: 'AdminController',
      resolve: {
        winesCount: ['AdminWines', function(Wines){
          return Wines.count();
        }],
        usersCount: ['AdminUsers', function(Users) {
          return Users.count();
        }],
        stacksCount: ['AdminStacks', function(Stacks) {
          return Stacks.count();
        }],
        database: ['Admin', function(Admin) {
          return Admin.database();
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.admin.page').controller('AdminController', [
  '$scope', 'AdminStacks', 'winesCount', 'usersCount', 'stacksCount', 'database', '$window', '$route',
  function ($scope, Stacks, winesCount, usersCount, stacksCount, database, $window, $route) {
    $scope.angularVersion = angular.version.full;
    $scope.mycellarVersion = mycellar.version.full;
    $scope.polymerVersion = window.Polymer.version;
    $scope.stacksCount = stacksCount;
    $scope.usersCount = usersCount;
    $scope.winesCount = winesCount;
    $scope.driver = database.driver;
    $scope.userName = database.userName;
    $scope.url= database.url;
    
    $scope.deleteAllStacks = function() {
      Stacks.deleteAll().$promise.then(function() {
        $route.reload();
      });
    };
    $scope.goToMonitoring = function() {
      $window.location.href = '/monitoring';
    };
  }
]);
