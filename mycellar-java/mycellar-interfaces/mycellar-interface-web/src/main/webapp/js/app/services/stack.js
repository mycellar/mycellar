'use strict';

function stackServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      resource: {
        list: $resource('/api/domain/stack/stacks'),
        item: $resource('/api/domain/stack/stack/:stackId')
      }
    };
  };
};

angular.module('mycellar')
  .provider('stackService', stackServiceProvider);
