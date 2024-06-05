function ajaxSessionTimeout() {
	window.location = $("#baseurl").val();
}

function ajaxAccesDenied() {
	window.location = $("#baseurl").val();
}

!function($) {
	$.ajaxSetup({
		statusCode : {
			901 : ajaxSessionTimeout,
			403 : ajaxAccesDenied,
			500 : ajaxAccesDenied
		}
	});
}(window.jQuery);

function jsValidarKey(e, pFormato, allowCopyPaste) {
	var charCode;
	var keyCode;
	var tecla;
	var teclaChar;

	var browser = navigator.appName;

	if (browser == "Microsoft Internet Explorer") {
		tecla = (document.all) ? event.keyCode : event.which;
		teclaChar = String.fromCharCode(tecla);

		charCode = event.which;
		keyCode = event.keyCode;
		tecla = charCode || keyCode;
		teclaChar = String.fromCharCode(tecla);

		if ((keyCode == 8) || (keyCode == 9) || (keyCode == 13)
				|| (keyCode == 39)) {
			return true;
		}

	} else {
		charCode = e.charCode;
		keyCode = e.keyCode;
		tecla = charCode || keyCode;
		teclaChar = String.fromCharCode(tecla);

		if ((keyCode == 8) || (keyCode == 9) || (keyCode == 13)
				|| (keyCode == 39) || (keyCode == 46)) {
			return true;
		}
	}

	return pFormato.test(teclaChar);
}

function validarTextArea(e, tamActual, tamMax) {
	var keyCode;
	var browser = navigator.appName;

	if (browser == "Microsoft Internet Explorer") {
		charCode = event.which;
		keyCode = event.keyCode;

		if ((keyCode == 8) || (keyCode == 9) || (keyCode == 13)
				|| (keyCode == 39)) {
			return true;
		} else if (tamActual >= tamMax) {
			return false;
		}

	} else {
		charCode = e.charCode;
		keyCode = e.keyCode;

		if ((keyCode == 8) || (keyCode == 9) || (keyCode == 13)
				|| (keyCode == 39) || (keyCode == 46)) {
			return true;
		} else if (tamActual >= tamMax) {
			return false;
		}
	}
	return true;
}
