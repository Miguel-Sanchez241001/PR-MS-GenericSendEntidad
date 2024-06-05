OPTIONS (SKIP=1)
LOAD DATA
APPEND
INTO TABLE BN_SATE.BNSATE15_CARGO_HIS
(
b15_fec_corte POSITION(44:55) "TO_DATE(:b15_fec_corte,'YYYYMMDD')",
b15_fec_proceso POSITION(195:202) "TO_DATE(:b15_fec_proceso,'YYYYMMDD')",
b15_cuenta_cargo POSITION(168:181),
b15_monto_cargado POSITION(76:90) "case when (:POSITION(54:56) is '053') then 0 else TO_NUMBER(SUBSTR(:b15_monto_cargado,1,13)||'.'||SUBSTR(:b15_monto_cargado,14,2),'9999999999999.99')",
b15_monto_pendiente POSITION(76:90) "case when (:POSITION(54:56) is '053') then TO_NUMBER(SUBSTR(:b15_monto_cargado,1,13)||'.'||SUBSTR(:b15_monto_cargado,14,2),'9999999999999.99') else 0",
b15_saldo_inicial POSITION(160:178),
b15_saldo_final POSITION(179:186)
)

