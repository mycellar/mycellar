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
}).filter('errorKeys', function () {
  return function (input) {
    var collectionKeys = [];
    for (var key in input) {
      if (input.hasOwnProperty(key) && key.charAt(0) != '$') {
        collectionKeys.push(key);
      }
    }
    return collectionKeys;
  }
});