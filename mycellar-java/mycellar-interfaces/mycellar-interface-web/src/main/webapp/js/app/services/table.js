'use strict';

function tableServiceProvider() {
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
        };
      }
    };
  };
};

angular.module('mycellar').provider('tableService', tableServiceProvider);