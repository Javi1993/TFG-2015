/**
 * Mensajes especiales al pasar el raton sobre los iconos
 */

$(document).ready(function () {

	// This will automatically grab the 'title' attribute and replace
	// the regular browser tooltips for all <a> elements with a title attribute!
	$('a[title]').qtip();
	$('img[title]').qtip();
	$('span[title]').qtip();
});