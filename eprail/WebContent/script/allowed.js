/**
 * JQuery para controlar los cambios en la tabla de los permisos
 */

$(document).ready(function(){
	$("input[type='checkbox'").change(function(){
		//Añadimos una C al nombre para saber que ese permiso se cambio
		$(this).attr("name", $(this).attr("name") + "C");
		$("#edit-s").removeAttr("disabled");
	});
});