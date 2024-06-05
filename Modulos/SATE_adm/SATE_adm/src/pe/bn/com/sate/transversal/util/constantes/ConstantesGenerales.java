package pe.bn.com.sate.transversal.util.constantes;

public class ConstantesGenerales {

	public static final int TIEMPO_MAXIMO_INACTIVIDAD = 1000 * 60 * 10;

	public static final String MENSAJE_SESION_EXPIRADA = "Estimado Usuario, su tiempo de sesi�n ha expirado";

	public static final String MENSAJE_SESION_INVALIDA = "Estimado Usuario, su sesi�n ha terminado. tiene otra sesi�n abierta";

	public static final String WS_SIN_CONEXION = "No se puede conectar al servicio web de autentica";

	public static final String DESC_VERSIONES = "Versiones";
	public static final String DESC_MENSAJES = "Mensajes";

	/**
	 * Constantes para los mensajes de validacion, operacion exitosa
	 */
	public static final int SEVERITY_INFO = 1;
	public static final int SEVERITY_WARN = 2;
	public static final int SEVERITY_ERROR = 3;
	public static final int SEVERITY_FATAL = 4;

	public static final String TITULO_MENSAJE = "Mensaje";

	// Constantes servicio web autentica
	public static String WS_CLAVES_OPERACION_EXISTOSA = "00";
	public static int LONGITUD_USUARIO = 4;
	public static int LONGITUD_CLAVE = 8;
	public static int WS_CLAVES_NUMERO_CARACTERES_PASSWORD = 8;
	public static String WS_CLAVES_CONSTID = "00";
	public static String WS_CLAVES_APP = "SATE";
	public static int WS_CLAVES_POSICION_STATUS = 0;
	public static int WS_CLAVES_POSICION_MENSAJE_FALLO_STATUS = 1;
	public static int WS_CLAVES_POSICION_CODIGO_AREA = 1;
	public static int WS_CLAVES_POSICION_CODIGO_EMPLEADO = 3;
	public static int WS_CLAVES_POSICION_PERMISOS = 4;
	public static int WS_CLAVES_POSICION_NOMBRES = 5;
	public static int WS_CLAVES_POSICION_AREA = 6;
	public static int WS_CLAVES_POSICION_CARGO = 11;
	public static int WS_CLAVES_POSICION_DNI = 9;

	// Constantes para los segmentos de nombre completo
	public static int WS_CLAVES_NOMBRE = 2;
	public static int WS_CLAVES_APPATERNO = 1;
	public static int WS_CLAVES_APMATERNO = 0;

	// Constantes de parametros
	public static final String TITULO_ERROR_AGREGAR_PARAMETRO = "ERROR AGREGAR PARAMETRO";
	public static final String TITULO_ERROR_EDITAR_PARAMETRO = "ERROR EDITAR PARAMETRO";
	public static final String TITULO_ERROR_OBTENER_PARAMETRO = "ERROR AL OBTENER PARAMETROS";

	public static final String ERROR_NEGOCIO_AFILIAR_EMPRESA = "Error al afiliar una empresa o unidad ejecutora en el negocio";
	public static final String ERROR_NEGOCIO_OBTENER_EMPRESA = "Error al obtener una empresa o unidad ejecutora en el negocio";

//	public static final String GENERAL_PERSISTENCE_ERROR = "Error en la BD, consulte con el administrador.";
	public static final String INTERNAL_PERSISTENCE_ERROR = "ERROR-001.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_SARASIGN_ERROR = "ERROR-002.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_BNTABLAS_ERROR = "ERROR-003.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_DATACOM_ERROR = "ERROR-004.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_WEB_SERVICE_MSG_ERROR = "ERROR-005.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_WEB_SERVICE_IGW_ERROR = "ERROR-006.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_WEB_SERVICE_BDUC_ERROR = "ERROR-007.Consulte con el administrador.";
	public static final String EXTERNAL_PERSISTENCE_WEB_SERVICE_COMP_ERROR = "ERROR-008.Consulte con el administrador.";

	// Constantes de resumenes
	public static final String TITULO_ERROR_OBTENER_RESUMEN = "ERROR AL OBTENER RESUMENES";

	// Mensaje validacion fechas
	public static final String ERROR_FECHA_ANTES = "La fecha inicial debe estar antes";

	// Ambientes
	public static final String AMBIENTE_DESA = "desa";
	public static final String AMBIENTE_CERT = "cert";
	public static final String AMBIENTE_PROD = "prod";

	// Funcionalidades accionadas
	public static final String MANT_PARAMETROS = "Mantenimiento de parametros";
	public static final String CONS_OPERACIONES_REALIZADAS = "Consultar operaciones realizadas";
	public static final String CONS_AFILIACIONES = "Consultar afiliaciones";
	public static final String CONS_RESUMENES_OPERATIVOS = "Consultar resumenes operativos";
	public static final String CONS_ACTIVIDADES_USUARIO = "Consultar actividades de usuario";

	// Acciones
	public static final String GENERAR_REPORTE = "Generar Reporte";
	public static final String REGISTRAR = "Registrar";
	public static final String MODIFICAR = "Modificar";
	public static final String CONSULTAR = "Consultar";

	// Keys parametros reporte
	public static final String KEY_AREA_REPORTE = "DIVISION";
	public static final String KEY_FECHA_INICIO = "fechaInicio";
	public static final String KEY_FECHA_FIN = "fechaFin";
	public static final String KEY_TIPO_REPORTE = "tipoReporte";

	// Tipo de reporte(tipo de extension)
	public static final String TIPO_REPORTE_PDF = "1";
	public static final String TIPO_REPORTE_EXCEL = "2";

	public static final int EMAIL_ORIGINAL = 1;
	public static final int EMAIL_ALTERNATIVO = 2;

	public static final int OPERACIONES_NO_ENCONTRADAS = 1;
	public static final int OPERACIONES_ENCONTRADAS = 2;
	public static final int BUSQUEDA_NO_REALIZADA = 0;

	// PARAMETRO COMP
	public static final String SISTEMA = "SATE";
	public static final String CUENTA = "USERSATE";
	public static final String SEMILLA = "semillaSATE";
	public static final String IDUSUARIO = "USERSATE";

	// GRUPO BDUC
	public final static String GRUPO_CONEXION_BDUC = "CONEXION_BDUC";
	public final static String PARAM_PERFIL2BDUC = "PERFIL2BDUC";
	public final static String PARAM_PERFILBDUC = "PERFILBDUC";
	public final static String PARAM_TIPODOCBDUC = "TIPODOCBDUC";
	public final static String PARAM_TRANSAC2BDUC = "TRANSAC2BDUC";
	public final static String PARAM_TRANSACBDUC = "TRANSACBDUC";
	public final static String PARAM_USERBDUC = "USERBDUC";

	// GRUPO DATACOM
	public final static String GRUPO_CONEXION_DATACOM = "CONEXION_DATACOM";
	public final static String PARAM_DRIVERDATACOM = "DRIVERDATACOM";
	public final static String PARAM_DBDATACOM = "DBDATACOM";
	public final static String PARAM_URLDATACOM = "URLDATACOM";
	public final static String PARAM_PORTDATACOM = "PORTDATACOM";
	public final static String PARAM_USERDATACOM = "USERDATACOM";
	public final static String PARAM_PASSDATACOM = "PASSDATACOM";
	public final static String PARAM_SYSTEMDATACOM = "SYSTEMDATACOM";
	public final static String PARAM_APPDATACOM = "APPDATACOM";
	public final static String PARAM_SERVERDATACOM = "SERVERDATACOM";
	public final static String PARAM_MAXCONNECTIONSDATACOM = "MAXCONNECTIONSDATACOM";
	public final static String PARAM_WAITTIMEDATACOM = "WAITTIMEDATACOM";
	public final static String PARAM_INITIALCONNECTIONSDATACOM = "INITIALCONNECTIONSDATACOM";

	// GRUPO TIEMPO
	public final static String GRUPO_TIEMPO = "TIEMPO";
	public final static String PARAM_SESIONEXPIRADATIEMPO = "SESIONEXPIRADATIEMPO";
	public final static String PARAM_CONEXIONTIEMPO = "CONEXIONTIEMPO";
	public final static String PARAM_RESPUESTATIEMPO = "RESPUESTATIEMPO";

	// CLAVE SEGURA
	public static final String RUTA_CLAVE_SEGURA = "C://opt//software//key//sate//clavesegurades.key";
//	public static final String RUTA_CLAVE_SEGURA = "//opt//software//key//sate//clavesegurades.key";

	/******************************* CLAVES DINAMICAS - C�DIGOS *************************************/
	public static final String CODIGO_BIN_AUTENTICACION_VIRTUAL = "1";
	public static final String CODIGO_TDC_BLOQUEADO = "B";
	public static final String CODIGO_TDC_REGISTRADO = "R";
	public static final String CODIGO_TDC_ACTIVADO = "A";
	public static final String CODIGO_TDC_ELIMINADO = "E";
	public static final String CODIGO_TIPO_ELEM_TDC = "2";
	public static final String CODIGO_TIPO_ELEM_TOKENS = "5";
	public static final Integer CODIGO_RESULTADO_OK = 0;
	public static final String CODIGO_OPERACION_OK = "1";

	public static final String MENSAJE_EXISTENCIA_CLAVE6 = "Si Usted no se encuentra afiliado a la clave de internet(6 D�gitos), ac�rquese a cualquiera de nuestras oficinas a nivel nacional para realizar su afiliaci�n. Si ya se encuentra afiliado, recuerde generar la clave en la opci�n 'Clave Internet'.";
	/******************************* CLAVES DINAMICAS - MENSAJES *************************************/

	public static final String MENSAJE_ERROR_GENERAR_ID_AFILIACION = "Error al generar el id de Afiliaci�n";
	public static final String MENSAJE_ERROR_INSERTAR_SOLICITUD_AFILIACION = "Error al guardar la solicitud de Afiliaci�n";
	public static final String MENSAJE_ERROR_CARGAR_PARAMETROS_TDC = "Error al cargar los parametros de la Tarjeta de Coordenadas o Token.";

	public static final String MENSAJE_EXISTENCIA_BIN_MEDIO_AUTENTICACION = "No existe el medio de autenticaci�n";

	public static final String MENSAJE_EXISTENCIA_MEDIO_AUTENTICACION = "No se encuentra afiliado al servicio Clave Din�mica (Tarjeta de Coordenadas o Token). Ac�rquese a cualquiera de nuestras oficinas a nivel nacional para realizar su afiliaci�n.";

	public static final String MENSAJE_ELEMENTO_SEGURIDAD_RELACIONADO = "No tiene una Tarjeta de Coordenadas o Token asignado";
	public static final String MENSAJE_CONSULTA_COORDENADAS_NO_DISPONIBLE = "Ha ocurrido un error al consultar la coordenada";
	public static final String MENSAJE_ACTIVAR_TARJETA_COORDENADAS = "La Tarjeta de Coordenadas o Token a�n no ha sido activado";
	public static final String MENSAJE_YA_ACTIVADA_TARJETA_COORDENADAS = "La tarjeta de coordenadas o Token ya se encuentra activa";
	public static final String MENSAJE_ERROR_ACTIVAR_TARJETA_COORDENADAS = "Ocurri� un Error al activar la Clave Dinamica";
	public static final String MENSAJE_ERROR_VENCIO_TARJETA_COORDENADAS = "Estimado cliente, el plazo para la activaci�n de su dispositivo ha caducado. Comun�quese a nuestra mesa de consultas a los tel�fonos 440-5305/442-4470 o l�nea gratuita 0-800-10-700 desde un tel�fono fijo.";
	public static final String MENSAJE_NO_BLOQUEADO_TARJETA_COORDENADAS = "No se pudo bloquear la Clave Din�mica";
	public static final String MENSAJE_ERROR_BLOQUEAR_TARJETA_COORDENADAS = "Ocurri� un Error al bloquear la Clave Dinamica";
	public static final String MENSAJE_TARJETA_COORDENADAS_BLOQUEADA = "La Tarjeta de Coordenadas o Token se encuentra bloqueado ";
	public static final String MENSAJE_TARJETA_COORDENADAS_ELIMINADA = "La Tarjeta de Coordenadas o Token se encuentra desvinculado";
	public static final String MENSAJE_YA_BLOQUEADA_TARJETA_COORDENADAS = "La tarjeta de Coordenadas o Token ya se encuentra bloqueado";
	public static final String MENSAJE_ERROR_CONSULTAR_TARJETA_COORDENADAS = "Ocurri� un Error al consultar la Clave Din�mica";
	public static final String MENSAJE_ERROR_CONSULTAR_TOKENS = "Ocurri� un Error al consultar el Token";
	public static final String MENSAJE_ERROR_VALIDAR_TARJETA_COORDENADAS = "Ocurri� un Error al validar la Tarjeta de Coordenadas";
	public static final String MENSAJE_ERROR_VALIDAR_TOKEN = "Ocurri� un Error al validar el Token";
	public static final String MENSAJE_BLOQUEAR_TARJETA_COORDENADAS_TEMP = "Error al ingresar la coordenada por tercera vez,por su seguridad el acceso se bloquear�.Vuelva a intentarlo despu�s de 24 horas.";
	public static final String MENSAJE_BLOQUEAR_TOKEN_TEMP = "Error al ingresar la clave de 6 digitos del Token por tercera vez,por su seguridad el acceso se bloquear�.Vuelva a intentarlo despues de 24 horas.";
	public static final String MENSAJE_BLOQUEAR_TARJETA_COORDENADAS_PER = "La Tarjeta de Coordenadas ha sido bloqueada por limite de intentos.Por su seguridad acerquese a la oficina mas cercana para tramitar nuevamente su Clave Din�mica";
	public static final String MENSAJE_BLOQUEAR_TOKEN_PER = "El Token ha sido bloqueado por l�mite de intentos.Por su seguridad acerquese a la oficina mas cercana para tramitar nuevamente su Clave Din�mica.";
	public static final String MENSAJE_INHABILITADA_TARJETA_COORDENADAS = "La Tarjeta de Coordenadas no esta habilitada.Comun�quese a nuestra mesa de consultas a los tel�fonos 440-5305/442-4470 o l�nea gratuita 0-800-10-700 desde un tel�fono fijo.";
	public static final String MENSAJE_INCORRECTO_CODIGO_TARJETA_COORDENADAS = "La coordenada ingresada es incorrecta.Ingresela nuevamente.";
	public static final String MENSAJE_INVALIDO_CODIGO_OPERACION = "Ha ocurrido un Error al realizar su operaci�n";
	public static final String MENSAJE_INVALIDO_RESULTADO_OPERACION = "El tiempo de la operaci�n caduco";
	public static final String MENSAJE_ERROR_CIFRADO = "Ocurri� un Error en el cifrado";
	public static final String MENSAJE_TOKEN_CADUCADO = "Estimado Cliente, el token a caducado.Acercarse a la oficina m�s cercana para tramitar su reposici�n";

	public static final String CODIGO_COMERCIO = "0027";
	public static final Integer TIPO_TERMINAL = 15;
	public static final String CODIGO_APLICACION_ORIGEN = "SATI";
	public static final String USUARIO = "0027";
	public static final String DIGITO_CHEQUEO_TOKENS = "0";

	public static final String LOGGER_DEBUG_NIVEL_1 = "1";
	public static final String LOGGER_DEBUG_NIVEL_2 = "2";

	public static String BN_LOGGER_PRINT_ERROR = null;
	public static String BN_LOGGER_PRINT_DEBUG_NIVEL_1 = null;
	public static String BN_LOGGER_PRINT_DEBUG_NIVEL_2 = null;

	public static String BN_ERR_LOGGER_FILE = null;
	public static String BN_ERR_LOGGER_CONSOLE = null;
	public static String BN_ERR_LOGGER_PATH = null;

	public static String BN_PROC_LOGGER_FILE = null;
	public static String BN_PROC_LOGGER_CONSOLE = null;
	public static String BN_PROC_LOGGER_PATH = null;
	public static String gBN_CONST_IMPRIMIR_TRAMA = "";

	public static final String ERR_LOGICA_NEGOCIO = "5000";
	public static final String ERR_CONECTION_DATACOM = "5001";
	public static final String ERR_ARCHIVO_NO_ENCONTRADO = "5002";
	public static final String ERR_ENCRIPTAR = "5003";
	public static final String ERR_WS_TRAMA_HOST = "5004";
	public static final String ERR_CONECTION_ORACLE_RENIEC = "5005";
	public static final String ERR_CONECTION_FEDERACION = "5006";
	public static final String ERR_OUT_MEMORY = "5007";
	public static final String ERR_OUT_CONECTION_BDUC = "5008";
	public static final String ERR_SEND_CORREO = "5009";
	public static final String ERR_CONECTION_SBS = "5010";
	public static final String ERR_FUNCION = "5011";
	public static final String ERR_CARGA_PARAMETROS_TDC = "5012";
	public static final String ERR_SESION = "5013";
	public static final String ERR_SOLICITAR_COORDENADA = "5014";
	public static final String ERR_ERROR_VALIDAR_TDC = "5015";
	public static final String CODIGO_CREDISCOTIA = "0095";
	public final static String gBN_CONST_PAGO_FACTURAS_CLARO = "2040";
	public final static String gBN_CONST_PAGO_FACTURAS_AMAG = "1060";
	public final static String gBN_CONST_PAGO_FACTURAS_UNIQUE = "2000";

	public static final String FLAG_MOSTRAR_TITULO_CLIENTE = "0";
	public static final String FLAG_MOSTRAR_TITULO_USUARIO = "1";

	/* CODIGOS RETORNO */

	public final static String LONGITUD_DEFECTO = "9999";
	public final static String SEG_LOGIN_TRAN = "WS56";

	public final static String ERROR_NULO_OUTPUT = "9989";

	public final static String ERROR_INTERFASE = "9984";
	public final static String ERROR_HOST = "9985";

	/* MENSAJES */
	public final static String MEN_BUS_ENT_INACTIVA = "LA EMPRESA ESTA INACTIVA";
	public final static String MEN_REG_ENT_EXISTE = "EMPRESA YA REGISTRADA EN BANCA EMPRESARIAL";
	public final static String MEN_REG_ENT_NO_EXISTE = "EMPRESA NO REGISTRADA EN BANCA EMPRESARIAL";
	public final static String MEN_REG_ENT_EXITO = "EMPRESA SE GRABO CORRECTAMENTE";
	public final static String MEN_REG_ENT_ERROR = "EMPRESA NO SE GRABO CORRECTAMENTE";
	public final static String MEN_MOD_ENT_EXITO = "EL ESTADO SE MODIFICO CORRECTAMENTE";
	public final static String MEN_ENT_SIN_RAZ = "SIN RAZON SOCIAL";

	public final static String MEN_REG_USR_EXITO = "USUARIO SE AFILIO A EMPRESA";
	public final static String MEN_REG_USR_ERROR = "USUARIO NO SE AFILIO CORRECTAMENTE";
	public final static String MEN_REG_USR_EXISTE = "USUARIO YA REGISTRADO EN EMPRESA";
	public final static String MEN_MOD_USR_EXITO = "USUARIO SE MODIFICO CORRECTAMENTE";

	public final static String MEN_REG_SER_EXITO = "SERVICIO SE GRABO CORRECTAMENTE";
	public final static String MEN_REG_SER_EXISTE = "SERVICIO YA REGISTRADO";

	public final static String MENS_TRAMA_ERROR = "ERROR DE TRAMA";
	public final static String MENS_TRAMA_NULA = "TRAMA NULA";
	public final static String MENS_ERROR_COMUNICACION = "ERROR DE COMUNICACION, COMUNIQUESE CON SOPORTE";
	public final static String MENS_ERROR_COMUNICACION_HOST = "ERROR DE COMUNICACION, COMUNIQUESE CON SOPORTE";
	public final static String MENS_ERROR_HOST = "HOST INCATIVO, COMUNIQUESE CON SOPORTE";
	public final static String MENS_ERROR_COMUNICACION_OPEN = "ERROR DE COMUNICACION EN BD_OPEN, COMUNIQUESE CON SOPORTE";// --
																															// OPEN

	// CONSTANTES CODIGO_REQUERIMIENTO ENVIO SMS Y CORREO
	public static Integer COD_REQUERIMIENTO_ENVIO_SMS = 52;
	public static Integer COD_REQUERIMIENTO_ENVIO_CORREO = 97;

	public static String CONST_RespSoN_S = "S";

	public static String ESTADO_EMPRESA_ACTIVO = "A";
	public static String ESTADO_EMPRESA_INACTIVO = "I";

	public static String ESTADO_USUARIO_ACTIVO = "A";
	public static String ESTADO_USUARIO_INACTIVO = "I";

	public static String ESTADO_SERVICIO_ACTIVO = "A";
	public static String ESTADO_SERVICIO_INACTIVO = "I";

	public static String ESTADO_DETSERV_ACTIVO = "A";
	public static String ESTADO_DETSERV_INACTIVO = "I";

	public static String ESTADO_CLAVE_ACTIVO = "A";
	public static String ESTADO_CLAVE_INACTIVO = "I";

	public static String ESTADO_TOKEN_ACTIVO = "A";
	public static String ESTADO_TOKEN_BLOQUEADO = "B";

	public static String ESTADO_TOKEN_DESBLOQUEADO = " ";

	public static String PERFIL_FIRMANTE = "F";
	public static String PERFIL_OPERADOR = "O";

	public static String CONST_TOKEN_DISPONIBLE = "D";
	public static String CONST_TOKEN_NODISPONIBLE = "N";

	public static String CONST_ACCION_INSERT = "I";
	public static String CONST_ACCION_UPDATE = "U";
	public static String CONST_ACCION_DELETE = "D";

	/* RETORNO HOST */
	public final static String CONS_HOST_EXITO = "00000";
	public final static String HOST_COD_RET_40042 = "40042";

	// HOST TIPO OPERACION
	public final static String GENERAR_CLAVE = "01";
	public final static String VERIFICAR_CLAVE = "02";
	public final static String CAMBIAR_CLAVE = "03";
	public final static String CAMBIAR_CLAVE_OLVIDO = "04";

	// SELECTS VACIOS
	public final static String DEPARTAMENTO_VACIO = "0";
	public final static String PROVINCIA_VACIO = "1";
	public final static String DISTRITO_VACIO = "2";

	// FLAGS
	public final static String DEBE_CAMBIAR_CLAVE = "1";
	public final static String OBJETO = "";

	// CONSTANTES PARAMETROS TABLA
	public static final int TIPO_VIA = 0;
	public static final int NOMBRE_VIA = 1;
	public static final int NUMERO_VIA = 2;
	public static final int DEPARTAMENTO = 3;
	public static final int OFICINA = 4;
	public static final int PISO = 5;
	public static final int MANZANA = 6;
	public static final int LOTE = 7;
	public static final int INTERIOR = 8;
	public static final int SECTOR = 9;
	public static final int KILOMETRO = 10;
	public static final int TIPO_ZONA = 11;
	public static final int NOMBRE_ZONA = 12;
	public static final int SIN_NUMERO_VIA = 13;
	public static final String URL_BASE_OPE = "14";
	
	//

}
