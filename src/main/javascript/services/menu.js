angular.module('mycellar.services.menu', ['ngResource']);

angular.module('mycellar.services.menu').factory('menuService', [
  '$resource', '$rootScope', '$location', 
  function ($resource, $rootScope, $location) {
    var menuService = {
        menus: []
    };
    
    menuService.reloadMenus = function() {
      menuService.menus = $resource('/api/navigation/menu').query();
    };
    
    return menuService;
  }
]);
