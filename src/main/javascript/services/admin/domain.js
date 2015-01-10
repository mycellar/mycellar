angular.module('mycellar.services.admin.domain', [
  'ngRoute',
  'mycellar.services.search',
  'mycellar.services.error'
]);

angular.module('mycellar.services.admin.domain').provider('adminDomainService', [
  '$routeProvider',
  function ($routeProvider) {
    var menu = [];
    var resourcePath = [];
    var resourcesPath = [];
    var domainParameters = [];
    this.$get = [
      '$location', '$route', '$injector', 'search', 'validityHelper', '$rootScope',
      function($location, $route, $injector, search, validityHelper, $rootScope) {
        var adminDomainService = {};
        $rootScope.$on('$routeChangeSuccess', function() {
          if ($location.path().indexOf('/admin/domain/') != 0) {
            adminDomainService.selected = -1;
          }
        });

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
         *          canSearch
         *          items
         *          result
         *        }
         */
        adminDomainService.listMethods = function(parameters) {
          angular.extend(parameters, domainParameters[parameters.group][parameters.resourceName]);
          parameters.canCreate = (parameters.canCreate != undefined ? parameters.canCreate : true);
          parameters.canDelete = (parameters.canDelete != undefined ? parameters.canDelete : true);
          parameters.canSearch = (parameters.canSearch != undefined ? parameters.canSearch : true);
          parameters.scope.errors = [];
          parameters.scope.items = parameters.items.list;
          parameters.scope.size = parameters.items.count;
          parameters.scope.pageCount = parameters.itemsPerPage;
          parameters.scope.result = parameters.result;
          parameters.scope.more = function() {
            var params = {
              first: parameters.scope.items.length,
              count: parameters.itemsPerPage,
              sort: parameters.sortParameter
            };
            if (parameters.canSearch && parameters.scope.search != null && parameters.scope.search.length > 2) {
              params['like'] = parameters.scope.search;
            }
            return $injector.get('Admin' + parameters.resourcesName).get(params, function(value) {
              parameters.scope.items = parameters.scope.items.concat(value.list);
            });
          };
          parameters.scope.edit = function(id) {
            $location.url(resourcePath[parameters.group][parameters.resourceName] + '/' + id);
          };
          if (parameters.canCreate) {
            parameters.scope.new = function() {
              $location.url(resourcePath[parameters.group][parameters.resourceName] + '/');
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
          if (parameters.canSearch) {
            var getMatches = function(inputValue) {
              if (inputValue === parameters.scope.search) {
                var params = {
                  first: 0,
                  count: parameters.itemsPerPage,
                  sort: parameters.sortParameter
                };
                if (inputValue != null && inputValue.length > 2) {
                  params['like'] = parameters.scope.search;
                }
                return $injector.get('Admin' + parameters.resourcesName).get(params, function(value) {
                  parameters.scope.items = value.list;
                  parameters.scope.size = value.count;
                });
              }
            };
            parameters.scope.toggleHidden = function() {
              search.toggleHidden();
              parameters.scope.search = '';
            };
            parameters.scope.clearSearch = function() {
              search.clearSearch();
              parameters.scope.search = '';
            };
            parameters.scope.$watch('search', function(newValue, oldValue) {
              if (newValue !== oldValue) {
                search.scheduleSearch(getMatches, newValue);
              }
            });
          }
        };

        adminDomainService.editMethods = function(parameters) {
          angular.extend(parameters, domainParameters[parameters.group][parameters.resourceName]);
          parameters.canSave = (parameters.canSave != undefined ? parameters.canSave : true);
          parameters.formName = parameters.formName || "form";
          parameters.scope.errors = [];
          parameters.scope.cancel = function () {
            $location.url(resourcesPath[parameters.group][parameters.resourceName]);
          };
          if (parameters.canSave) {
            parameters.scope.save = function () {
              var self = this;
              self.backup = {};
              self.errors = [];
              angular.copy(parameters.resource, self.backup);
              parameters.resource.$save().then(function (value, headers) {
                self.backup = undefined;
                $location.url(resourcesPath[parameters.group][parameters.resourceName]);
              }, function (response) {
                if (response.status === 400 && response.data.errorKey != undefined) {
                  angular.forEach(response.data.properties, function(property) {
                    if (self[parameters.formName][property] != undefined) {
                      validityHelper.sinceChanged(self[parameters.formName][property], response.data.errorKey);
                    }
                  });
                  self.errors.push(response.data.errorKey);
                  angular.copy(self.backup, parameters.resource);
                } else {
                  self.errors.push('Unknown error: ' + response.status);
                  angular.copy(self.backup, parameters.resource);
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
