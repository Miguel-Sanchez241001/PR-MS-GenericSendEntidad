OPTIONS (SKIP=1)
LOAD DATA
APPEND
INTO TABLE BN_SATE.BNSATE12_RPTA_MC_TEMP
(
b12_numero_cuenta POSITION(39:57),
b12_codigo_asignacion POSITION(24:38),
b12_tipo_respuesta "TO_NUMBER('3')",
b12_fecha_inicio_linea POSITION(97:104) "TO_DATE(:b12_fecha_inicio_linea,'YYYYMMDD')", 
b12_fecha_fin_linea POSITION(105:112) "TO_DATE(:b12_fecha_fin_linea,'YYYYMMDD')", 
b12_fecha_registro "SYSDATE"
)