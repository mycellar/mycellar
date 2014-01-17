angular.module('mycellar.services.admin.domain', [
  'mycellar.services.table',
  'ngRoute'
]);

angular.module('mycellar.services.admin.domain').provider('adminDomainService', [
  '$routeProvider', 
  function ($routeProvider) {
    var menu = [];
    var resourcePath = [];
    var resourcesPath = [];
    this.$get = [
      '$location', '$route', 'tableService', 
      function($location, $route, tableService) { 
        var adminDomainService = {};
        
        adminDomainService.getMenu = function() {
          return menu;
        };
        
        adminDomainService.listMethods = function(group, resourceName, resource, defaultSort, canDelete, canCreate) {
          canCreate = (canCreate != undefined ? canCreate : true);
          canDelete = (canDelete != undefined ? canDelete : true);
          var extension = {
            'tableOptions': {
              itemResource: resource.get,
              defaultSort: defaultSort
            },
            tableContext: tableService.createTableContext(),
            errors: []
          };
          extension.edit = function(id) {
            $location.path(resourcePath[group][resourceName] + '/' + id);
          };
          if (canCreate) {
            extension.new = function() {
              $location.path(resourcePath[group][resourceName] + '/');
            };
          }
          if (canDelete) {
            extension.delete = function(id) {
              var errors = this.errors;
              resource.deleteById(id, function (value, headers) {
                if (value.errorKey != undefined) {
                  errors.push(value);
                } else {
                  $route.reload();
                }
              });
            };
          }
          return extension;
        };
        
        adminDomainService.editMethods = function(group, resourceName, resource, formName, canSave) {
          canSave = (canSave != undefined ? canSave : true);
          var extension = {
            errors: []
          };
          extension.cancel = function () {
            $location.path(resourcesPath[group][resourceName]);
          };
          if (canSave) {
            extension.save = function () {
              var self = this;
              extension.backup = {};
              selft.errors = [];
              angular.copy(resource, extension.backup);
              resource.$save(function (value, headers) {
                if (value.errorKey != undefined) {
                  angular.forEach(value.properties, function(property) {
                    if (self[formName][property] != undefined) {
                      self[formName][property].$setValidity(value.errorKey, false);
                    }
                  });
                  self.errors.push(value);
                  angular.copy(extension.backup, resource);
                } else {
                  extension.backup = undefined;
                  $location.path(resourcesPath[group][resourceName]);
                }
              });
            };
          }
          return extension;
        };
        
        return adminDomainService;
      }
    ];
    
    this.forDomain = function(group, resourceName, resourcesName, groupLabel, resourcesLabel) {
      var baseUrl = function(name) {
        return '/admin/domain/' + group + '/' + name.charAt(0).toLowerCase() + name.substr(1);
      };
      
      var templateUrl = function(name) {
        return '/partials' + baseUrl(name) + '.tpl.html';
      };
      
      var controllerName = function(name) {
        return 'AdminDomain' + name + 'Controller';
      };
      
      var resourceRoute = baseUrl(resourceName) + '/:id?';
      var resourcesRoute = baseUrl(resourcesName);
      
      // This is the object that our `forDomain()` function returns.  It decorates `$routeProvider`,
      // delegating the `when()` and `otherwise()` functions but also exposing some new functions for
      // creating CRUD routes.  Specifically we have `whenList() and `whenEdit()`.
      var routeBuilder = {
        // Create a route that will handle showing a list of items
        whenCrud: function(resolveListFns, resolveEditFns) {
          var menuGroup = null;
          angular.forEach(menu, function (item) {
            if (item.label == groupLabel) {
              menuGroup = item;
            }
          });
          if (menuGroup == null) {
            menu.push({label: groupLabel, menus: [{label: resourcesLabel, route: resourcesRoute}]});
            menu.sort(function(a,b) { return a.label.toUpperCase().localeCompare(b.label.toUpperCase()) });
          } else {
            menuGroup.menus.push({label: resourcesLabel, route: resourcesRoute});
            menuGroup.menus.sort(function(a,b) { return a.label.toUpperCase().localeCompare(b.label.toUpperCase()) });
          }
          if (resourcePath[group] == undefined) {
            resourcePath[group] = [];
            resourcesPath[group] = [];
          }
          resourcePath[group][resourceName] = baseUrl(resourceName);
          resourcesPath[group][resourceName] = baseUrl(resourcesName);
          routeBuilder.when(resourcesRoute, {
            templateUrl: templateUrl(resourcesName),
            controller: controllerName(resourcesName),
            resolve: resolveListFns
          });
          routeBuilder.when(resourceRoute, {
            templateUrl: templateUrl(resourceName),
            controller: controllerName(resourceName),
            resolve: resolveEditFns
          });
          return routeBuilder;
        },
        // Pass-through to `$routeProvider.when()`
        when: function(path, route) {
          $routeProvider.when(path, route);
          return routeBuilder;
        },
        // Pass-through to `$routeProvider.otherwise()`
        otherwise: function(params) {
          $routeProvider.otherwise(params);
          return routeBuilder;
        },
        // Access to the core $routeProvider.
        $routeProvider: $routeProvider
      };
      return routeBuilder;
    };
  }
]);
