#!/bin/bash

#DECLARANDO VARIABLES
readonly LOGDIR=/d02/data/sate
readonly CONSQL=$1/$2@//$3:$4/$5
readonly LOGFILE='SATE.LOG'

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

#LIMPIEZA DE TABLAS HIS
escribirLineaLog "INICIO DE LIMPIEZA DE TABLAS HIS"
sqlplus -S $CONSQL <<CONEXIONSQL
set feedback off
execute BN_SATE.BNPD_11_TRUNCAR_HIS();
exit;
CONEXIONSQL
codigoError=$?
escribirLineaLog "FIN DE LIMPIEZA DE TABLAS HIS" $codigoError
