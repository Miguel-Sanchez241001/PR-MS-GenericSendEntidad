#!bin/bash

#DECLARANDO VARIABLES
readonly DATADIR=$1
readonly LOGDIR=$2
readonly DATAFILE='ENVIO_BLOQUEOS.TXT'
readonly LOGFILE='ENVIO_BLOQUEOS.LOG'
readonly CONFTP=$3
readonly CONSQL=$4

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

escribirLineaLog "************INICIO DEL SCRIPT - ENVIAR BLOQUEOS******************"
#GENERACION DEL ARCHIVO CON LAS TRAMAS DE LOS BLOQUEOS
escribirLineaLog "INICIO DE GENERACION DE TRAMAS DE BLOQUEOS"
sqlplus -S $CONSQL <<CONEXIONSQL > /dev/null
set serveroutput on feedback off
set linesize 1000
spool $DATADIR/$DATAFILE
execute BN_SATE.BNPD_03_BLOQUEO_GEN_1();
spool off
exit;
CONEXIONSQL
codigoError=$?
##
if [ $codigoError -eq 0 ]
	then
	escribirLineaLog "FIN DE GENERACION DE TRAMAS DE BLOQUEOS" $codigoError
	#ENVIO DE ARCHIVOS AL SERVIDOR FTP
	escribirLineaLog "INICIO DE ENVIO DE ARCHIVO AL FTP"
	curl --silent -T $DATADIR/$DATAFILE $CONFTP/$DATAFILE
	codigoError=$?
	##
	if [ $codigoError -eq 0 ]
		then
		rm -f $DATADIR/$DATAFILE
		escribirLineaLog "FIN DE ENVIO DE ARCHIVO AL FTP" $codigoError
		#ACTUALIZACION DE ESTADO DE TARJETAS
		escribirLineaLog "INICIO ACTUALIZACION DE ESTADO DE TARJETAS"
sqlplus -S $CONSQL <<CONEXIONSQL
set serveroutput on feedback off
execute BN_SATE.BNPD_04_BLOQUEO_GEN_2();
exit;
CONEXIONSQL
		codigoError=$?
		##
		if [ $codigoError -eq 0 ]
			then
			escribirLineaLog "FIN ACTUALIZACION DE FLAG DE BLOQUEOS" $codigoError
			else
			escribirLineaLog "ERROR EN LA ACTUALIZACION DE FLAG DE BLOQUEOS" $codigoError
		fi
	else
		escribirLineaLog "ERROR EN EL ENVIO DE ARCHIVO AL FTP" $codigoError
	fi
else
	escribirLineaLog "ERROR EN LA GENERACION DE BLOQUEOS" $codigoError
fi
escribirLineaLog "************FIN DEL SCRIPT - ENVIAR BLOQUEOS******************"
#
