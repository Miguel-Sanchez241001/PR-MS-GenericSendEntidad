OPTIONS (SKIP=1)
LOAD DATA
APPEND
INTO TABLE BNSATE13_RPTA_MEF_TEMP
(
b13_sec_operacion POSITION(2:16),
b13_tipo_operacion POSITION(17:19),
b13_cuenta_cargo POSITION(20:39),
b13_tipo_documento POSITION(40:41),
b13_num_documento POSITION(42:61),
b13_nombre_beneficiario POSITION(62:161),
b13_num_tarjeta_aut POSITION(162:177),
b13_fec_inicio_aut POSITION(178:185) "TO_DATE(:b13_fec_inicio_aut,'YYYYMMDD')",
b13_fec_fin_aut POSITION(186:193) "TO_DATE(:b13_fec_fin_aut,'YYYYMMDD')",
b13_importe POSITION(194:208) "TO_NUMBER(SUBSTR(:b13_importe,1,13)||'.'||SUBSTR(:b13_importe,14,2),'9999999999999.99')",
b13_sec_operacion_ref POSITION(209:223),
b13_fecha_registro "SYSDATE"
)