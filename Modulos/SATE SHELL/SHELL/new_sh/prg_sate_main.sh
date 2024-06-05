#!/bin/bash

#DECLARANDO VARIABLES
readonly SHELLDIR=/d02/programas/sh/sate
readonly DATADIR=/d02/data/sate
readonly LOGDIR=/d02/data/sate
readonly CTLDIR=/d02/programas/control/sate
readonly CONSQL=${1}/${2}@//${3}:${4}/${5}
readonly CONFTP=ftp://${6}:${7}@${8}:${9}
readonly CONSFTP=sftp://${10}:${11}@${12}:${13}
readonly LOGFILE='SATE.LOG'
readonly READMCDIR='sftp/SATE/in/McProcesos'
readonly WRITEMCDIR='ftp_sate/in/McProcesos'
readonly READMEFDIR='sftp/SATE/in/MEF'

#FUNCIONES
escribirLineaLog(){
fechaTiempo="$(date +'[%d/%m/%Y %H:%M:%S]:')"
if [ -z "$2" ]
then
echo $fechaTiempo $1 >> $LOGDIR/$LOGFILE
else
echo "$fechaTiempo $1, codigo de error: $2" >> $LOGDIR/$LOGFILE
fi
}

#LIMPIEZA DE LOGS
rm -r -f $DATADIR/*

#LIMPIEZA DE TABLAS TEMP
escribirLineaLog "INICIO DE LIMPIEZA DE TABLAS TEMP"
sqlplus -S $CONSQL <<CONEXIONSQL
set feedback off
execute BN_SATE.BNPD_10_TRUNCAR_TEMP();
exit;
CONEXIONSQL
codigoError=$?
escribirLineaLog "FIN DE LIMPIEZA DE TABLAS TEMP" $codigoError

#CREACION DE VARIABLE DE ENTORNO LOCAL
export NLS_LANG=SPANISH_SPAIN.WE8MSWIN1252

#EJECUTAR SHELLS DE RECEPCION DE TRAMAS
sh $SHELLDIR/solicitud/prg_sate_solicitud_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL
sh $SHELLDIR/bloqueo/prg_sate_bloqueo_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL
sh $SHELLDIR/contacto/prg_sate_contacto_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL
sh $SHELLDIR/asignacion/prg_sate_asignacion_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL
sh $SHELLDIR/cuenta/prg_sate_cuenta_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMEFDIR $CONSQL
sh $SHELLDIR/transaccion/prg_sate_transaccion_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL
sh $SHELLDIR/cargo/prg_sate_cargo_recepcion_desa.sh $DATADIR $LOGDIR $CTLDIR $CONSFTP/$READMCDIR $CONSQL

#EJECUTAR SHELLS DE ENVIO DE TRAMAS
sh $SHELLDIR/solicitud/prg_sate_solicitud_envio_desa.sh $DATADIR $LOGDIR $CONFTP/$WRITEMCDIR $CONSQL
sh $SHELLDIR/bloqueo/prg_sate_bloqueo_envio_desa.sh $DATADIR $LOGDIR $CONFTP/$WRITEMCDIR $CONSQL
sh $SHELLDIR/contacto/prg_sate_contacto_envio_desa.sh $DATADIR $LOGDIR $CONFTP/$WRITEMCDIR $CONSQL
