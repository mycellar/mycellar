angular.module('services.menu', ['ngResource']);

angular.module('services.menu').factory('menuService', ['$resource', '$rootScope', '$location', function ($resource, $rootScope, $location) {

  var menu = [];
  var menuService = {};
  
  menuService.analyzePath = function() {
    var path = $location.path();
    if (path.match("/admin/domain")) {
      path = "/admin/domains";
    } else if (path.match("/booking/contact")) {
      path = "/booking/contacts";
    }
    angular.forEach(menu, function(item) {
      if (item.route == undefined) {
        var found = false;
        angular.forEach(item.pages, function(page) {
          if (page.route == path) {
            page.active = true;
            found = true;
          } else {
            page.active = false;
          }
        });
        item.active = found;
      } else {
        item.active = item.route == path;
      }
    });
  };
  
  $rootScope.$on('$routeChangeSuccess', menuService.analyzePath);
  
  menuService.getAll = function() {
    return menu;
  };
  
  menuService.reloadMenus = function() {
    menu = $resource('/api/navigation/menu').query(function() {
      menuService.analyzePath();
    });
  };
  
  menuService.reloadMenus();
  
  return menuService;
}]);
