package pe.bn.com.sate.persistence.mapper.internal;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.bn.com.sate.transversal.dto.sate.Parametro;

public interface ParametroMapper {

	@Select("SELECT BN_SATE.BNSQ_16_MOV_NUM_REFERENCIA.nextval from dual ")
	public long obtenerNumeroReferenciaMovimientos();

	@Select("SELECT BN_SATE.BNSQ_16_SAL_NUM_REFERENCIA.nextval from dual ")
	public long obtenerNumeroReferenciaSaldos();

	@Select("SELECT BN_SATE.BNSQ_16_MOD_NUM_REFERENCIA.nextval from dual ")
	public long obtenerNumeroReferenciaModificacion();

	@Select("SELECT BN_SATE.BNSQ_16_SAL_NUM_REFERENCIA.nextval from dual ")
	public long obtenerNumeroModificarTarjeta();

	@Select("SELECT * FROM bn_sate.BNSATE16_parametro WHERE B16_ID_TABLA in (0,2,3,5,6,7,8,9,10,11,13) and B16_ID_REGISTRO !=0")
	@ResultMap("mapParametro")
	public List<Parametro> buscarParametrosDireccionMC();

	@Select("SELECT * FROM bn_sate.BNSATE16_parametro WHERE B16_ID_TABLA = #{idTabla} and B16_ID_REGISTRO =#{idRegistro} ")
	@ResultMap("mapParametro")
	public Parametro buscarParametro(@Param("idTabla") String idTabla,
			@Param("idRegistro") String idRegistro);

}
