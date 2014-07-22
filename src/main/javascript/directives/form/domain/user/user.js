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
          scope.subPostLabel = ' de l\'utilisateur';
        }
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
        form: '=',
        user: '=',
        postLabel: '@',
        label: '@',
        subPostLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      link: function(scope, iElement, iAttrs, controller, transcludeFn) {
        if (scope.label == null || scope.label == '') {
          scope.label = 'Utilisateur';
        }
        if (scope.subPostLabel == null || scope.subPostLabel == '') {
          scope.subPostLabel = ' de l\'utilisateur';
        }
      },
      controller: [
        '$scope', '$location', '$filter', 'AdminUsers',
        function($scope, $location, $filter, AdminUsers) {
          var userFilter = $filter('userRenderer');
          $scope.renderUser = function(user) {
            if (user != null) {
              return userFilter(user);
            } else {
              return '';
            }
          };
          var resource = AdminUsers;
          $scope.errors = [];
          $scope.users = resource.like;
          $scope.new = function() {
            $scope.newUser = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            $scope.errors = [];
            resource.validate($scope.newUser, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subUserForm[property] != undefined) {
                    $scope.subUserForm[property].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.user = $scope.newUser;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
