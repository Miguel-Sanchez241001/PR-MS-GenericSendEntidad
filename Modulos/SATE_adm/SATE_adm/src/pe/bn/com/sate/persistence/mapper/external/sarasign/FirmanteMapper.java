package pe.bn.com.sate.persistence.mapper.external.sarasign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;

import com.ibm.websphere.ce.cm.StaleConnectionException;

public interface FirmanteMapper {

	@Select("select distinct "
			+ "person.TIPDOC as TIPO_DOCUMENTO,person.NUMCED as NUMERO_DOCUMENTO,"
			+ "isnull(person.TXTNAM,'') as NOMBRES, isnull(person.LSTNAM+' ','') as APPATERNO, isnull(person.FAMNAM+' ','') as APMATERNO"
			+ " "
			+ "from [BN_SARASIGN].[dbo].[UTB_FIRMANTES_PJ] firm "
			+ "inner join [BN_SARASIGN].[dbo].[UTB_PERSONAS] person on firm.coddoc=person.CODDOC and firm.numdoc=person.numdoc "
			+ "inner join [BN_SARASIGN].[dbo].[UTB_CUENTAS] cuent on firm.NUMACC=cuent.NUMACC "
			+ "inner join [BN_SARASIGN].[dbo].[UTB_EMPRESAS] emp on cuent.CODORG=emp.CODORG "
			+ "inner join [BN_SARASIGN].[dbo].[UTB_CONDICIONES_FIRMANTES_PJ] cond on firm.NUMACC=cond.NUMACC and firm.NUMSGN=cond.NUMSGN "
			+ "inner join [BN_SARASIGN].[dbo].[UTB_FACULTADES] fac on cond.CODFAC=fac.CODFAC and cond.IDEFAC=fac.IDEFAC "
			+ "where emp.TXTRUC = #{numeroRuc} "
			+ "and  (firm.IDESTA='D' or firm.IDESTA='A') and person.TIPDOC is not null and person.NUMCED is not null "
			+ "and cuent.FLGESTADO='0' and cuent.IDEACC='J' and person.TIPDOC in ('001','002','017','005') "
			+ "and (fac.TXTFAC like '%FORZOS%' OR fac.TXTFAC like 'MANCOMUNAD%' OR fac.TXTFAC LIKE '%SOLA FIRMA%' OR fac.TXTFAC LIKE '3 FIRMAS%') "
			+ "order by NUMERO_DOCUMENTO")
	@ResultMap("mapFirmante")
	public List<Firmante> buscarFirmantes(@Param("numeroRuc") String numeroRuc);
	
	@Select("Select 1")
	public String probarConexion();
}
