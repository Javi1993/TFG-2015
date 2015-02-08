/**
 * JQuery de formularios
 */

$(document).ready(function(){

	function comparePass(){
		if($("#pass").val()!=$("#newpass").val()){
			$("#edit-s").attr("disabled","disabled");
			alert("Las contrase\u00f1a no coinciden");
		}else{
			$("#edit-s").removeAttr("disabled");
		}
	}

	$("#pass").change(function(){
		if($("#pass").val()!="" && $("#confirm").css("display")=="inline" && $("#newpass").val()!=""){
			comparePass();
		}
		else if($("#pass").val()!="")
		{
			$("#confirm").css("display","inline");
			$("#edit").css("height","250");
			$("#edit").css("margin-top","-185");
			$("#newpass").attr("required","required");
			alert("Confirma tu contrase\u00f1a en el nuevo campo habilitado");
		}else{
			$("#newpass").removeAttr("required");
			$("#edit-s").removeAttr("disabled");
			$("#confirm").css("display","none");
			$("#edit").css("height","230");
			$("#edit").css("margin-top","-175");
		}
	});

	$("#newpass").change(function(){
		if($("#pass").val()!="" && $("#confirm").css("display")=="inline" && $("#newpass").val()!=""){
			comparePass();
		}else{
			$("#edit-s").removeAttr("disabled");
		}
	});

	$('input.reg-i').each(function(){
		$(this).after('*');
	});
});