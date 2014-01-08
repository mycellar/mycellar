angular.module('mycellar.directives.form.domain.user.user', [
  'mycellar.resources.user.users'
]);

angular.module('mycellar.directives.form.domain.user.user').directive('userForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/user/user-form.tpl.html',
      scope: {
        form: '=',
        user: '=',
        postLabel: '@',
        label: '@',
        subPostLabel: '@'
      },
      link: function(scope, iElement, iAttrs, controller, transcludeFn) {
        if (scope.label == null || scope.label == '') {
          scope.label = 'Utilisateur';
        }
        if (scope.subPostLabel == null || scope.subPostLabel == '') {
          scope.subPostLabel = 'de l\'utilisateur';
        }
      }
    }
  }
]).directive('user', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/user/user.tpl.html',
      scope: {
        form: '=',
        user: '=',
        postLabel: '@',
        label: '@',
        subPostLabel: '@'
      },
      link: function(scope, iElement, iAttrs, controller, transcludeFn) {
        if (scope.label == null || scope.label == '') {
          scope.label = 'Utilisateur';
        }
        if (scope.subPostLabel == null || scope.subPostLabel == '') {
          scope.subPostLabel = 'de l\'utilisateur';
        }
      },
      controller: function($scope, Users, $filter) {
        var userFilter = $filter('userRenderer');
        $scope.renderUser = function(user) {
          if (user != null) {
            return userFilter(user);
          } else {
            return '';
          }
        };
        $scope.users = Users.nameLike;
        $scope.new = function() {
          $scope.newUser = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Users.validate($scope.newUser, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subUserForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subUserForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.user = $scope.newUser;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
