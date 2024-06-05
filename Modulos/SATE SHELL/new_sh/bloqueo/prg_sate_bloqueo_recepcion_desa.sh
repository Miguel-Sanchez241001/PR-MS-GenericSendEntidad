#DECLARANDO VARIABLES
readonly DATADIR=$1
readonly LOGDIR=$2
readonly CTLDIR=$3
readonly DATAFILEPATTERN='^AD312[0-9]{6}\.(txt|TXT)$'
declare DATAFILE=''
readonly CTLFILE='CTL_BLOQUEO.CTL'
readonly CTLLOGFILE='CTL_BLOQUEO.LOG'
readonly LOGFILE='RECEPCION_BLOQUEO.LOG'
readonly CONSFTP=$4
readonly CONSQL=$5

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

cargarDataFile(){
codigoError=$?
if ! [ -z $1 ]
then	
	sorted=$( printf '%s\n' "$@" | sort -nr )
	for arg in $sorted[@]
	do
		if [[ $arg =~ $DATAFILEPATTERN ]]
		then
			DATAFILE=$arg
			break
		fi
	done;
fi
}

escribirLineaLog "*****************INICIO DEL SCRIPT - RECIBIR BLOQUEOS******************"
cargarDataFile $(curl --silent -k $CONSFTP/ -l)
if ! [ -z $DATAFILE ]
then
#OBTENCION DE LOS ARCHIVOS DE CARGA PARA LAS TABLAS DE REGLAS Y LA TABLA TEMPORAL
escribirLineaLog "INICIO DE OBTENCION DE ARCHIVOS EN EL FTP"
curl --silent -k $CONSFTP/$DATAFILE -o $DATADIR/$DATAFILE
codigoError=$?
##
if [ $codigoError -eq 0 ]
then
	escribirLineaLog "FIN DE OBTENCION DE ARCHIVOS EN EL FTP" $codigoError
	#LLENADO DE LA DATA A LAS TABLAS DE REGLAS Y LA TABLA TEMPORAL
	escribirLineaLog "INICIO DE EJECUCION DE CTL"

	sqlldr userid=$CONSQL control=$CTLDIR/$CTLFILE data=$DATADIR/$DATAFILE log=$DATADIR/$CTLLOGFILE silent=HEADER, FEEDBACK
	codigoError=$?
	##
	if [ $codigoError -eq 0 ]
	then
		escribirLineaLog "FIN DE EJECUCION DE CTL" $codigoError
		#ELIMINACION DE LOS ARCHIVOS DE CARGA PARA LAS TABLAS DE REGLAS Y LA TABLA TEMPORAL
		escribirLineaLog "INICIO DE ELIMINACION DE ARCHIVOS OBTENIDOS DEL FTP"
		rm -f $DATADIR/$DATAFILE
		codigoError=$?
		##
		if [ $codigoError -eq 0 ]
		then
			escribirLineaLog "FIN DE ELIMINACION DE ARCHIVOS OBTENIDOS DEL FTP" $codigoError
			#EJECUCION DE PROCEDURES
			escribirLineaLog "INICIO DE EJECUCION DE PROCEDIMIENTO"
sqlplus -S $CONSQL <<CONEXIONSQL
set serveroutput on feedback off
execute BN_SATE.BNPD_05_BLOQUEO_ACT();
exit;
CONEXIONSQL
			codigoError=$?
			##
			if [ $codigoError -eq 0 ]
			then
				escribirLineaLog "FIN DE EJECUCION DE PROCEDIMIENTO" $codigoError
			else
				escribirLineaLog "ERROR EN LA EJECUCION DE PROCEDIMIENTO" $codigoError
			fi

		else
			escribirLineaLog "ERROR EN LA EJECUCION DE CTL" $codigoError
		fi
	else
		escribirLineaLog "ERROR EN LA ELIMINACION DE ARCHIVOS OBTENIDOS DEL FTP" $codigoError
	fi
else
	escribirLineaLog "ERROR EN LA OBTENCION DE ARCHIVOS EN EL FTP" $codigoError
fi
else
	escribirLineaLog "NO SE ENCONTRO EL ARCHIVO EN EL FTP" $codigoError
fi
escribirLineaLog "*******************FIN DEL SCRIPT - RECIBIR BLOQUEOS*******************"
