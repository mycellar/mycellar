angular.module('mycellar.services.menu', ['ngResource']);

angular.module('mycellar.services.menu').factory('menuService', [
  '$resource', '$rootScope', '$location', 
  function ($resource, $rootScope, $location) {
    var menu = [];
    var menuService = {};
    
    menuService.getAll = function() {
      return menu;
    };
    
    menuService.reloadMenus = function() {
      menu = $resource('/api/navigation/menu').query();
    };
    
    return menuService;
  }
]);
