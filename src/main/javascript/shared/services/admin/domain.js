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
    var domainParameters = [];
    this.$get = [
      '$location', '$route',
      function($location, $route) { 
        var adminDomainService = {};
        
        adminDomainService.getMenu = function() {
          return menu;
        };
        
        /**
         * @param parameters {
         *          scope: the controller scope
         *          group: the group name
         *          resourceName: the name of the resource
         *          resource: the resource service
         *          canDelete
         *          canCreate
         *          tableContext
         *          result
         *        }
         */
        adminDomainService.listMethods = function(parameters) {
          angular.extend(parameters, domainParameters[parameters.group][parameters.resourceName]);
          parameters.canCreate = (parameters.canCreate != undefined ? parameters.canCreate : true);
          parameters.canDelete = (parameters.canDelete != undefined ? parameters.canDelete : true);
          parameters.scope.errors = [];
          parameters.scope.tableContext = parameters.tableContext;
          parameters.scope.result = parameters.result;
          parameters.scope.edit = function(id) {
            $location.path(resourcePath[parameters.group][parameters.resourceName] + '/' + id);
          };
          if (parameters.canCreate) {
            parameters.scope.new = function() {
              $location.path(resourcePath[parameters.group][parameters.resourceName] + '/');
            };
          }
          if (parameters.canDelete) {
            parameters.scope.delete = function(id) {
              var errors = this.errors;
              parameters.resource.delete({id: id}, function (value, headers) {
                if (value.errorKey != undefined) {
                  errors.push(value);
                } else {
                  $route.reload();
                }
              });
            };
          }
        };
        
        adminDomainService.editMethods = function(parameters) {
          angular.extend(parameters, domainParameters[parameters.group][parameters.resourceName]);
          parameters.canSave = (parameters.canSave != undefined ? parameters.canSave : true);
          parameters.formName = parameters.formName || "form";
          parameters.scope.errors = [];
          parameters.scope.cancel = function () {
            $location.path(resourcesPath[parameters.group][parameters.resourceName]);
          };
          if (parameters.canSave) {
            parameters.scope.save = function () {
              var self = this;
              self.backup = {};
              self.errors = [];
              angular.copy(parameters.resource, self.backup);
              parameters.resource.$save(function (value, headers) {
                if (value.errorKey != undefined) {
                  angular.forEach(value.properties, function(property) {
                    if (self[parameters.formName][property] != undefined) {
                      self[parameters.formName][property].$setValidity(value.errorKey, false);
                    }
                  });
                  self.errors.push(value.errorKey);
                  angular.copy(self.backup, parameters.resource);
                } else {
                  self.backup = undefined;
                  $location.path(resourcesPath[parameters.group][parameters.resourceName]);
                }
              });
            };
          }
        };
        
        return adminDomainService;
      }
    ];
    
    /**
     * @param parameters {
     *          group: the group name
     *          resourceName: the name of the resource
     *          resourcesName: the name of the resource in plural form
     *          groupLabel: the group label
     *          resourcesLabel: the label of the resource in plural form
     *          defaultSort: the default sort for list view,
     *          canCreate
     *        }
     */
    this.forDomain = function(parameters) {
      var baseUrl = function(name) {
        return '/admin/domain/' + parameters.group + '/' + name.charAt(0).toLowerCase() + name.substr(1);
      };
      
      var templateUrl = function(name) {
        return '/partials/views' + baseUrl(name) + '.tpl.html';
      };
      
      var controllerName = function(name) {
        return 'AdminDomain' + name + 'Controller';
      };
      
      parameters.canCreate = (parameters.canCreate != undefined ? parameters.canCreate : true);
      var resourceRoute = baseUrl(parameters.resourceName) + '/:id';
      if (parameters.canCreate) {
        resourceRoute += '?';
      }
      var resourcesRoute = baseUrl(parameters.resourcesName);
      
      // This is the object that our `forDomain()` function returns.  It decorates `$routeProvider`,
      // delegating the `when()` and `otherwise()` functions but also exposing some new functions for
      // creating CRUD routes.
      var routeBuilder = {
        // Create a route that will handle showing a list of items
        whenCrud: function(resolveListFns, resolveEditFns) {
          var menuGroup = null;
          angular.forEach(menu, function (item) {
            if (item.label == parameters.groupLabel) {
              menuGroup = item;
            }
          });
          if (menuGroup == null) {
            menu.push({label: parameters.groupLabel, menus: [{label: parameters.resourcesLabel, route: resourcesRoute}]});
            menu.sort(function(a,b) { return a.label.toUpperCase().localeCompare(b.label.toUpperCase()) });
          } else {
            menuGroup.menus.push({label: parameters.resourcesLabel, route: resourcesRoute});
            menuGroup.menus.sort(function(a,b) { return a.label.toUpperCase().localeCompare(b.label.toUpperCase()) });
          }
          if (resourcePath[parameters.group] == undefined) {
            resourcePath[parameters.group] = [];
            resourcesPath[parameters.group] = [];
          }
          resourcePath[parameters.group][parameters.resourceName] = baseUrl(parameters.resourceName);
          resourcesPath[parameters.group][parameters.resourceName] = baseUrl(parameters.resourcesName);
          if (domainParameters[parameters.group] == undefined) {
            domainParameters[parameters.group] = [];
          }
          domainParameters[parameters.group][parameters.resourceName] = parameters;
          
          routeBuilder.when(resourcesRoute, {
            templateUrl: templateUrl(parameters.resourcesName),
            controller: controllerName(parameters.resourcesName),
            resolve: angular.extend({
              tableContext: [
                'tableService', 'Admin' + parameters.resourcesName,
                function(tableService, resource) {
                  var tableContext = tableService.createTableContext(resource.get, parameters.defaultSort);
                  return tableContext.setPage(1).promise;
                }
              ]
            }, resolveListFns)
          });
          routeBuilder.when(resourceRoute, {
            templateUrl: templateUrl(parameters.resourceName),
            controller: controllerName(parameters.resourceName),
            resolve: angular.extend({
              item: [
                '$route', 'Admin' + parameters.resourcesName,
                function($route, resource) {
                  var id = $route.current.params.id;
                  if (id != null) {
                    return resource.get({id: $route.current.params.id}).$promise;
                  } else {
                    return new resource();
                  }
                }
              ]
            }, resolveEditFns)
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
