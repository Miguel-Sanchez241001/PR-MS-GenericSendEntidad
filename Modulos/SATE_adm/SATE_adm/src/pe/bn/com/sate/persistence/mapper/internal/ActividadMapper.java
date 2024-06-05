package pe.bn.com.sate.persistence.mapper.internal;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.bn.com.sate.transversal.dto.sate.Actividad;

//@Transactional(value="sate",propagation = Propagation.MANDATORY)
public interface ActividadMapper {

	@Select("select * from BN_SATE.BNSATE17_ACT_USUARIO where B02_ID_USU = #{idUsuario} and trunc(B17_FECHA) Between To_Date(#{fechaInicio},'dd/mm/yy') AND To_Date(#{fechaFin}, 'dd/mm/yy') order by B17_FECHA DESC")
	@ResultMap("mapObtenerActividadesPorUsuario")
	public List<Actividad> buscarActividades(
			@Param("idUsuario") Long idUsuario,
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin);
}
