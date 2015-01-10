angular.module('mycellar.directives.navigation', [
  'mycellar.services.menu'
]);

angular.module('mycellar.directives.navigation').directive('navigation', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/navigation.tpl.html',
      link: function(scope, element, attrs) {
        element[0].addEventListener('core-select', function(e) {
          if (e.detail.isSelected) {
            element[0].parentElement.togglePanel();
          }
        });
      },
      controller: [
        '$scope', 'menuService', '$location',
        function ($scope, menuService, $location) {
          $scope.menuService = menuService;
          $scope.goto = function(menu) {
            $location.url(menu.route);
          }
          $scope.year = new Date().getFullYear();
        }
      ]
    }
  }
]);