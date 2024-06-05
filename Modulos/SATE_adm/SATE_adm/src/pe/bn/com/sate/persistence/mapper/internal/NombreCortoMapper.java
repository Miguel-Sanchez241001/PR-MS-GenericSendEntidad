package pe.bn.com.sate.persistence.mapper.internal;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.bn.com.sate.transversal.dto.sate.NombreCorto;


//@Transactional(value="sate",propagation = Propagation.MANDATORY)
public interface NombreCortoMapper {

	@Select("SELECT B09_NOMBRE FROM BN_SATE.BNSATE09_NOMBRE_CORTO " +
			"WHERE B09_NUM_RUC = #{numeroRuc} ")
	@ResultMap("mapNombreCorto")
	public NombreCorto obtenerNombreCortoPorRuc(@Param("numeroRuc") String numeroRuc);
	
}
