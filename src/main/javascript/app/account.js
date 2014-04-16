angular.module('mycellar.controllers.account', [
  'ngRoute',
  'mycellar.services.security'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/account', {
      templateUrl: 'partials/views/account.tpl.html',
      controller: 'AccountController'
    });
  }
]);

angular.module('mycellar.controllers.account').controller('AccountController', [
  '$scope', '$rootScope', 'security',
  function ($scope, $rootScope, security) {
    $scope.hasProfile = false;
    $scope.hasBooking = false;
    $scope.hasCellar = false;
    $scope.hasAdmin = false;
    $scope.userEmail = '';
    $scope.userName = '';
    $rootScope.$watch('currentUser', function() {
      manageCurrentUser();
    });
    var manageCurrentUser = function() {
      if ($rootScope.currentUser != null) {
        $scope.hasProfile = $rootScope.currentUser.profile != null;
        if ($rootScope.currentUser.profile == 'BOOKING') {
          $scope.hasBooking = true;
        } else if ($rootScope.currentUser.profile == 'CELLAR') {
          $scope.hasCellar = true;
        } else if ($rootScope.currentUser.profile == 'ADMIN') {
          $scope.hasBooking = true;
          $scope.hasCellar = true;
          $scope.hasAdmin = true;
        } else if ($rootScope.currentUser.profile == 'MYCELLAR') {
          $scope.hasBooking = true;
          $scope.hasCellar = true;
        }
        $scope.userEmail = $rootScope.currentUser.email;
        $scope.userName = $rootScope.currentUser.name;
      }
    };
    var resetScope = function() {
      $scope.email = '';
      $scope.email2 = '';
      $scope.oldPassword = '';
      $scope.password = '';
      $scope.password2 = '';
    };
    resetScope();
    manageCurrentUser();

    $scope.changePasswordFormDisplayed = false;
    $scope.changeEmailFormDisplayed = false;

    $scope.hideForms = function() {
      $scope.changeEmailFormDisplayed = false;
      $scope.changePasswordFormDisplayed = false;
      resetScope();
    };
    $scope.displayChangePasswordForm = function() {
      $scope.hideForms();
      $scope.changePasswordFormDisplayed = true;
    };
    $scope.displayChangeEmailForm = function() {
      $scope.hideForms();
      $scope.changeEmailFormDisplayed = true;
    };

    $scope.changePassword = function() {
      security.changePassword($scope.oldPassword, $scope.password);
      $scope.hideForms();
    };
    $scope.changeEmail = function() {
      security.changeEmail($scope.email, $scope.password);
      $scope.hideForms();
    };
  }
]);
