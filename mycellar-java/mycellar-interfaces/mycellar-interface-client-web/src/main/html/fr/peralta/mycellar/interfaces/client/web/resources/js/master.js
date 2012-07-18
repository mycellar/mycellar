/**
 * Init method.
 */
$(function() {
  // Init bootstrap dropdown.
  $('.dropdown-toggle').dropdown();
  // Init bootstrap collapse.
  $('.collapse').collapse();
  // Init bootstrap datepicker.
  $("input[type='date']").datepicker({
	  format: 'dd/mm/yyyy',
	  autoclose: true,
	  weekstart: 2
  });
  $('.confirm').mousedown(function() { if (confirm('Merci de confirmer l\'action.')) { $(this).mouseup(); $(this).click();  } });
});