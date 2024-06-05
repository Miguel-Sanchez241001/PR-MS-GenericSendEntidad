#!/bin/ksh
sqlplus -S $1/$2@//$3:$4/$5 <<EOF > archivo.txt
set serveroutput on pagesize 0 linesize 1000 termout off feedback off
execute BNPD_00_SOLICITUDES();
