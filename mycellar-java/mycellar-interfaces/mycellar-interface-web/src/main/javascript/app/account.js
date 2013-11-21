angular.module('mycellar.controllers.account', [
  'ngRoute',
  'mycellar.services.security.service'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/account', {
      templateUrl: 'partials/account.tpl.html',
      controller: 'AccountController'
    });
  }
]);

angular.module('mycellar.controllers.account').controller('AccountController', [
  '$scope', 'security',
  function ($scope, security) {
    $scope.hasProfile = false;
    $scope.hasBooking = false;
    $scope.hasCellar = false;
    $scope.hasAdmin = false;
    $scope.userEmail = '';
    $scope.userName = '';
    $scope.$watch(function() {
      return security.currentUser;
    }, function(value) {
      manageCurrentUser();
    });
    var manageCurrentUser = function() {
      if (security.currentUser != null) {
        $scope.hasProfile = security.currentUser.profile != null;
        if (security.currentUser.profile == 'BOOKING') {
          $scope.hasBooking = true;
        } else if (security.currentUser.profile == 'CELLAR') {
          $scope.hasCellar = true;
        } else if (security.currentUser.profile == 'ADMIN') {
          $scope.hasBooking = true;
          $scope.hasCellar = true;
          $scope.hasAdmin = true;
        } else if (security.currentUser.profile == 'MYCELLAR') {
          $scope.hasBooking = true;
          $scope.hasCellar = true;
        }
        $scope.userEmail = security.currentUser.email;
        $scope.userName = security.currentUser.name;
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