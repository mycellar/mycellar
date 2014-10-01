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
        passwords: '='
      }
    }
  }
]).directive('user', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/user/user.tpl.html',
      scope: {
        user: '=',
        label: '@'
      },
      link: function(scope, element, attrs) {
        element[0].$.control.addEventListener('input', function() {
          scope.input = element[0].$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          element[0].possibles = value;
        });
        element[0].render = scope.renderUser;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setUser(value);
          scope.$apply();
        };
        element[0].value = scope.user;
      },
      controller: [
        '$scope', '$location', '$filter', 'AdminUsers',
        function($scope, $location, $filter, AdminUsers) {
          var resource = AdminUsers;

          $scope.input = '';
          $scope.$watch('input', function() {
            if ($scope.input.length > 2) {
              resource.like($scope.input).then(function(value) {
                $scope.possibles = value;
              });
            } else {
              $scope.possibles = [];
            }
          });

          var userFilter = $filter('userRenderer');
          $scope.renderUser = function(user) {
            if (user != null) {
              return userFilter(user);
            } else {
              return '';
            }
          };
          $scope.setUser = function(user) {
            if (user != null) {
              resource.get({id: user.id}, function(value) {
                $scope.user = value;
              });
            } else {
              $scope.user = null;
            }
          }
        }
      ]
    }
  }
]);
