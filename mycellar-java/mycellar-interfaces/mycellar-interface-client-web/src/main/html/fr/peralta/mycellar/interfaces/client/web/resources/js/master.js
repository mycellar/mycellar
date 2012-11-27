/**
 * Init method.
 */
$(function() {
  // Init bootstrap dropdown.
  $('.dropdown-toggle').dropdown();
  // Init bootstrap collapse.
  $('.collapse').collapse();
  $('.confirm').mousedown(function() { if (confirm('Merci de confirmer l\'action.')) { $(this).mouseup(); $(this).click();  } });
});