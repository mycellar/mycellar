angular.module('mycellar.directives.admin.domain-nav', [
  'mycellar.services.admin.domain'
]);

angular.module('mycellar.directives.admin.domain-nav').directive('adminDomainNav', [
  'adminDomainService',
  function(adminDomainService) {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/admin/domain-nav.tpl.html',
      link: function(scope, element, attrs) {
        element[0].children[0].selected = adminDomainService.selected;
        element[0].addEventListener('core-select', function(e) {
          if (e.detail.isSelected) {
            adminDomainService.selected = element[0].children[0].selected;
            element[0].children[0].selected = adminDomainService.selected;
            element[0].parentElement.parentElement.togglePanel();
          }
        });
      },
      controller: [
        '$scope', '$location',
        function($scope, $location) {
          $scope.currentLocation = $location.path();
          $scope.menu = adminDomainService.getMenu();
          $scope.goto = function(menu) {
            $location.url(menu.route);
          };
        }
      ]
    }
  }
]);
