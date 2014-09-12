angular.module('mycellar.services.admin.domain', [
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
      '$location', '$route', '$injector',
      function($location, $route, $injector) {
        var adminDomainService = {};
        
        adminDomainService.getMenu = function() {
          return menu;
        };
        
        /**
         * @param parameters {
         *          scope: the controller scope
         *          group: the group name
         *          resourceName: the name of the resource
         *          canDelete
         *          canCreate
         *          items
         *          result
         *        }
         */
        adminDomainService.listMethods = function(parameters) {
          angular.extend(parameters, domainParameters[parameters.group][parameters.resourceName]);
          parameters.canCreate = (parameters.canCreate != undefined ? parameters.canCreate : true);
          parameters.canDelete = (parameters.canDelete != undefined ? parameters.canDelete : true);
          parameters.scope.errors = [];
          parameters.scope.items = parameters.items.list;
          parameters.scope.size = parameters.items.count;
          parameters.scope.pageCount = parameters.itemsPerPage;
          parameters.scope.result = parameters.result;
          parameters.scope.more = function() {
            
          };
          parameters.scope.edit = function(id) {
            $location.path(resourcePath[parameters.group][parameters.resourceName] + '/' + id);
          };
          if (parameters.canCreate) {
            parameters.scope.new = function() {
              $location.path(resourcePath[parameters.group][parameters.resourceName] + '/');
            };
          }
          if (parameters.canDelete) {
            parameters.scope.delete = function(id, event) {
              var errors = this.errors;
              $injector.get('Admin' + parameters.resourcesName).delete({id: id}, function (value, headers) {
                if (value.errorKey != undefined) {
                  errors.push(value);
                } else {
                  $route.reload();
                }
              });
              event.stopPropagation();
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
     *          defaultSort: the default sort for list view
     *          itemsPerPage
     *          canCreate
     *        }
     */
    this.forDomain = function(parameters) {
      var initSort = function(defaultSort) {
        var sort = {
          properties: [],
          ways: {}
        };
        var sortBy = function(property) {
          if (sort.ways[property] == 'asc') {
            sort.ways[property] = 'desc';
          } else if (sort.ways[property] == 'desc') {
            sort.properties.splice(sort.properties.indexOf(property), 1);
            sort.ways[property] = null;
          } else {
            sort.properties.push(property);
            sort.ways[property] = 'asc';
          }
        };
        angular.forEach(defaultSort, function(sort) {
          sortBy(sort);
        });
        var sortParameter = [];
        for (var t in sort.properties) {
          sortParameter.push(sort.properties[t] + ',' + sort.ways[sort.properties[t]]);
        }
        return sortParameter;
      };
      var baseUrl = function(name) {
        return '/admin/domain/' + parameters.group + '/' + name.charAt(0).toLowerCase() + name.substr(1);
      };
      
      var templateUrl = function(name) {
        return '/partials/views' + baseUrl(name) + '.tpl.html';
      };
      
      var controllerName = function(name) {
        return 'AdminDomain' + name + 'Controller';
      };
      
      parameters.itemsPerPage = parameters.itemsPerPage || 10;
      parameters.sortParameter = initSort(parameters.defaultSort);
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
              items: [
                'Admin' + parameters.resourcesName,
                function(resource) {
                  return resource.get({
                    first: 0,
                    count: parameters.itemsPerPage,
                    sort: parameters.sortParameter
                  }).$promise;
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
