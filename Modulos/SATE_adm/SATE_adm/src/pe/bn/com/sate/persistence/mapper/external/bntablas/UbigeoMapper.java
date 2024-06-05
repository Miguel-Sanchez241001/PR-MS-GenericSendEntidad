package pe.bn.com.sate.persistence.mapper.external.bntablas;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.bn.com.sate.transversal.dto.external.bntablas.Ubigeo;

public interface UbigeoMapper {

	@Select("SELECT distinct (f02_cdepartamento) as B08_COD_DEPARTAMENTO,f02_departamento as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo]")
	@ResultMap("mapUbigeo")
	public List<Ubigeo> buscarDepartamentos();

	@Select("SELECT distinct (f02_cprovincia) as B08_COD_PROVINCIA,f02_cdepartamento as B08_COD_DEPARTAMENTO,f02_provincia as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo] where f02_cdepartamento =#{codDepartamento}")
	@ResultMap("mapUbigeo")
	public List<Ubigeo> buscarProvinciasPorDepartamento(
			@Param("codDepartamento") String codDepartamento);

	@Select("SELECT distinct (f02_cdistrito) as B08_COD_DISTRITO,f02_distrito as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo] where f02_cdepartamento =#{codDepartamento} and f02_cprovincia =#{codProvincia}")
	@ResultMap("mapUbigeo")
	public List<Ubigeo> buscarDistritosPorProvincia(
			@Param("codDepartamento") String codDepartamento,
			@Param("codProvincia") String codProvincia);

	@Select("SELECT f02_departamento as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo] where f02_cubigeo = #{ubigeo}")
	@ResultMap("mapUbigeo")
	public Ubigeo buscarDepartamento(@Param("ubigeo") String ubigeo);
	
	@Select("SELECT f02_provincia as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo] where f02_cubigeo = #{ubigeo}")
	@ResultMap("mapUbigeo")
	public Ubigeo buscarProvincia(@Param("ubigeo") String ubigeo);
	
	@Select("SELECT f02_distrito as B08_DESCRIPCION FROM [BN_Tablas].[dbo].[BNTGF02_Ubigeo] where f02_cubigeo = #{ubigeo}")
	@ResultMap("mapUbigeo")
	public Ubigeo buscarDistrito(@Param("ubigeo") String ubigeo);
	
	@Select("Select 1")
	public String probarConexion();
}
