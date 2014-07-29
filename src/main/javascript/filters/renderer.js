angular.module('mycellar').filter('wineRenderer', function() {
  return function(wine) {
    return wine.producer.name +
        (wine.name != null && wine.name.length > 0 ? ' - ' + wine.name : '') +
        (wine.vintage != null ? ' - ' + wine.vintage : '');
  };
}).filter('bookingEventRenderer', function(dateFilter) {
  return function(bookingEvent) {
    return bookingEvent.name + ' (du ' + dateFilter(bookingEvent.start, 'longDate') +
        ' au ' + dateFilter(bookingEvent.end, 'longDate') + ')';
  };
}).filter('formatRenderer', function(numberFilter) {
  return function(format) {
    return format.name + ' (' + numberFilter(format.capacity) + 'L)';
  }
}).filter('userRenderer', function() {
  return function(user) {
    return user.lastname + ' ' + user.firstname;
  }
}).filter('wineTypeRenderer', function() {
  return function(type) {
    switch(type) {
    case 'STILL':
      return 'Tranquille';
    case 'SPARKLING':
      return 'Pétillant';
    case 'SPIRIT':
      return 'Spiritueux';
    case 'OTHER':
      return 'Autre';
    }
    return color;
  };
}).filter('wineColorRenderer', function() {
  return function(color) {
    switch(color) {
    case 'RED':
      return 'Rouge';
    case 'WHITE':
      return 'Blanc';
    case 'ROSE':
      return 'Rosé';
    case 'OTHER':
      return 'Autre';
    }
    return color;
  };
}).filter('accessRightRenderer', function() {
  return function(accessRight) {
    switch(accessRight) {
    case 'READ':
      return 'Lecture seule';
    case 'MODIFY':
      return 'Modification';
    }
    return accessRight;
  };
}).filter('movementTypeRenderer', function() {
  return function(type) {
    switch(type) {
    case 'I':
      return 'Entrée';
    case 'O':
      return 'Sortie';
    }
    return type;
  };
});
