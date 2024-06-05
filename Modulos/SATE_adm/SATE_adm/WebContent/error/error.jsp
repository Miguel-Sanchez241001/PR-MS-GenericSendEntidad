<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>


<h:head>
	<title>Sistema de Administraci&oacute;n de Tarjetas
		Empresariales</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta http-equiv="Content-Language" content="es" />

	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/tipografias.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/mejoras-login.css" />
</h:head>
<h:body class="body-bg">
	<center>
	<br></br>
	<br></br>
			<div class="div-error sombra-inferior">
			
			<h1 class="letras-grandes">ERROR ${requestScope['javax.servlet.error.status_code']}</h1>
	
			<span class="texto-contenido">${requestScope['javax.servlet.error.message']}</span>
	<br></br>
		<img src="${pageContext.request.contextPath}/resources/img/chica-mal.png"/>
			<br></br>
			<input type="button" value="Regresar"  onclick="window.location='/SATE_adm';" />
			</div>
	</center>
</h:body>
</html>