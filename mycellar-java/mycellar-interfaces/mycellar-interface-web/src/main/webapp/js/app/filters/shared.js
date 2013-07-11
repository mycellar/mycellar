'use strict';

angular.module('mycellar').filter('firstLine', function () {
  return function (input) {
    var out = "";
    for (var i = 0; i < input.length; i++) {
      if (input.charAt(i) == '\r' || input.charAt(i) == '\n') {
        break;
      }
      out = out + input.charAt(i);
    }
    return out;
  }
}).filter('localDate', function () {
  return function (input) {
    // to fix
    return input[2] + '/' + input[1] + '/' + input[0];
  }
});