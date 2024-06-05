OPTIONS (SKIP=1)
LOAD DATA
APPEND
INTO TABLE BN_SATE.BNSATE12_RPTA_MC_TEMP
WHEN (77:80) = '0001'
(
b12_TRACE POSITION(53:58),
b12_tipo_documento POSITION(87:87),
b12_numero_documento POSITION(88:99),
b12_apellidos POSITION(100:129),
b12_nombre POSITION(130:159),
b12_numero_cuenta POSITION(160:178),
b12_fecha_apertura_cuenta POSITION(179:186) "TO_DATE(:b12_fecha_apertura_cuenta,'DDMMYYYY')",
b12_bloqueo_1_cuenta POSITION(187:187),
b12_bloqueo_2_cuenta POSITION(188:188),
b12_numero_tarjeta POSITION(189:207),
b12_fecha_apertura_tarjeta POSITION(208:215) "TO_DATE(:b12_fecha_apertura_tarjeta,'DDMMYYYY')",
b12_bloqueo_1_tarjeta POSITION(216:216),
b12_fecha_vencimiento_tarjeta POSITION(217:224) "TO_DATE(:b12_fecha_vencimiento_tarjeta,'DDMMYYYY')",
b12_codigo_producto POSITION(225:227),
b12_linea_credito POSITION(228:236) "TO_NUMBER(:b12_linea_credito)",
b12_tipo_respuesta "TO_NUMBER('0')"
b12_fecha_registro "SYSDATE"
)