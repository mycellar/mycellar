angular.module('mycellar.services.table', []);

angular.module('mycellar.services.table').provider('tableService', [function () {
  this.$get = function() {
    return {
      createTableContext: function() {
        return {
          filtersIsCollapsed: true,
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
          }
        };
      }
    };
  };  
}]);