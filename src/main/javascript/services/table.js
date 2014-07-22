angular.module('mycellar.services.table', [
  'mycellar.services.pagination'
]);

angular.module('mycellar.services.table').provider('tableService', [function ($q) {
  this.$get = ['$q', 'paginationService', function($q, paginationService) {
    return {
      createTableContext: function(query, defaultSort, parameters) {
        var tableContext = {
          filtersIsCollapsed: true,
          query: query,
          pageRange: 6,
          itemsPerPage: paginationService.getItemsPerPage() || 10,
          currentPage: paginationService.getCurrentPage() || 1,
          total: paginationService.getTotal(),
          firstItem: null,
          parameters: parameters || {},
          sort: {
            properties: [],
            ways: {}
          },
          filters: {},
          sortBy: function(property) {
            if (this.sort.ways[property] == 'asc') {
              this.sort.ways[property] = 'desc';
            } else if (this.sort.ways[property] == 'desc') {
              this.sort.properties.splice(this.sort.properties.indexOf(property), 1);
              this.sort.ways[property] = null;
            } else {
              this.sort.properties.push(property);
              this.sort.ways[property] = 'asc';
            }
          },
          clearFilters: function() {
            this.filters = {};
          },
          isAsc: function(property) {
            return this.sort.ways[property] == 'asc';
          },
          isDesc: function(property) {
            return this.sort.ways[property] == 'desc';
          },
          orderByFilterPredicates: function() {
            var value = [];
            for (key in this.sort.ways) {
              if (this.sort.ways[key] != null) {
                value.push((this.isAsc(key) ? '+' : '-') + key);
              }
            }
            return value;
          },
          setPage: function() {
            this.firstItem = (this.currentPage - 1) * this.itemsPerPage;
            var sortParameter = [];
            for (var t in this.sort.properties) {
              sortParameter.push(this.sort.properties[t] + ',' + this.sort.ways[this.sort.properties[t]]);
            }
            var filtersParameter = [];
            for (var t in this.filters) {
              filtersParameter.push(t + ',' + this.filters[t]);
            }
            this.result = this.query(angular.extend({}, this.parameters, {
              first: this.firstItem,
              count: this.itemsPerPage,
              sort: sortParameter,
              filters: filtersParameter
            }));
            var deferred = $q.defer();
            this.result.$promise.then(function(value) {
              deferred.resolve(tableContext);
            }, function(value) {
              deferred.reject(tableContext);
            });

            // SAVE PARAMETERS :
            // total needed for angular-ui pagination
            paginationService.setCurrentPage(this.currentPage);
            paginationService.setItemsPerPage(this.itemsPerPage);
            paginationService.setTotal(this.total);
            paginationService.setSort(sortParameter);
            paginationService.setFilters(filtersParameter);

            return deferred;
          }
        };
        var storedSort = paginationService.getSort();
        if (storedSort != null) {
          var table = storedSort.split(',');
          if (table.length > 1) {
            for(var i = 0; i < table.length; i++) {
              var property = table[i++];
              tableContext.sort.properties.push(property);
              tableContext.sort.ways[property] = table[i];
            }
          }
        } else {
          angular.forEach(defaultSort, function(sort) {
            tableContext.sortBy(sort);
          });
        }
        return tableContext;
      }
    };
  }];  
}]);