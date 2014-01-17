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
}).filter('error', function() {
  return function (input) {
    if (input == 'email') {
      return "Veuillez saisir une adresse mail correcte.";
    } else if (input == 'equals') {
      return "Les deux champs ne sont pas égaux.";
    } else if (input == 'editable') {
      return "Veuillez sélectionner une valeur présente dans la liste.";
    } else {
      return input;
    }
  }
});
