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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderUser;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setUser(value);
          scope.$apply();
        };
        scope.$watch('user', function() {
          autocomplete.value = scope.user;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newUser = {};
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newUser = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newUser, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subUserForm[property] != undefined) {
                    $scope.subUserForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.user = $scope.newUser;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
