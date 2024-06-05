package pe.bn.com.sate.persistence.mapper.internal;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EmpresaResumen;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;

import com.ibm.websphere.ce.cm.StaleConnectionException;

//@Transactional(propagation = Propagation.MANDATORY)
public interface EmpresaMapper {

	@Insert("INSERT INTO BN_SATE.BNSATE00_EMPRESA(B00_NUM_CUENTA_CORRIENTE,B00_NUM_RUC,B00_TIPO,B00_RAZON_SOCIAL,B00_DIRECCION,B00_NOMBRE_CORTO,B00_CIC,B00_TELEFONO,B00_REFERENCIA,B00_UBIGEO,B00_DIRECCION_MC) "
			+ "values (#{cuentaCorriente},#{ruc},#{tipo},#{razonSocial},#{direccion},#{nombreCorto},#{cic},#{telefonoFijo},#{referencia},#{ubigeo},#{direccionMc})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "B00_ID_EMP")
	public void registrarEmpresa(Empresa empresa);

	@Insert("INSERT INTO BN_SATE.BNSATE01_EST_EMPRESA (B00_ID_EMP,B01_FEC_AFILIACION,B01_USUARIO_AFI) "
			+ "VALUES (#{idEmpresa},#{fechaAfiliacion},#{usuarioAfiliacion})")
	public void registrarEstadoEmpresa(EstadoEmpresa estadoEmpresa);

	@Update("UPDATE BN_SATE.BNSATE01_EST_EMPRESA SET B01_FEC_DESAFILIACION = #{fechaDesafiliacion}, B01_MOTIVO = #{motivo}, "
			+ "B01_DETALLE= #{detalle}, B01_USUARIO_DESA =#{usuarioDesafiliacion} WHERE  B01_ID_EST = #{id}")
	public void bloquearEmpresa(EstadoEmpresa estadoEmpresa);

	@Select("SELECT * FROM BN_SATE.BNSATE01_EST_EMPRESA WHERE B01_ID_EST = (SELECT MAX(BN01.B01_ID_EST) "
			+ "FROM BN_SATE.BNSATE01_EST_EMPRESA BN01 WHERE BN01.B00_ID_EMP = (SELECT BN00.B00_ID_EMP FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_NUM_RUC = #{ruc}))")
	@ResultMap("mapEstadoEmpresa")
	public EstadoEmpresa buscarEstadoEmpresa(@Param("ruc") String ruc);

	@Select("SELECT * FROM BN_SATE.BNSATE00_EMPRESA BEMP "
			+ "INNER JOIN BN_SATE.BNSATE01_EST_EMPRESA BEST ON BEMP.B00_ID_EMP = BEST.B00_ID_EMP AND BEST.B01_ID_EST = (SELECT MAX(BN01.B01_ID_EST) "
			+ "FROM BN_SATE.BNSATE01_EST_EMPRESA BN01 WHERE BN01.B00_ID_EMP = (SELECT BN00.B00_ID_EMP FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_NUM_RUC = BEMP.B00_NUM_RUC)) "
			+ "AND ((TRUNC(BEST.B01_FEC_AFILIACION) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy'))) OR (TRUNC(BEST.B01_FEC_DESAFILIACION) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy'))))")
	@ResultMap("mapEmpresas")
	public List<EmpresaResumen> buscarEmpresas(
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin);

	@Select("SELECT * FROM BN_SATE.BNSATE00_EMPRESA BEMP "
			+ "INNER JOIN BN_SATE.BNSATE01_EST_EMPRESA BEST ON BEMP.B00_ID_EMP = BEST.B00_ID_EMP "
			+ "AND ((TRUNC(BEST.B01_FEC_AFILIACION) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy'))) OR (TRUNC(BEST.B01_FEC_DESAFILIACION) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy')))) order by BEMP.B00_NUM_RUC,BEST.B01_FEC_AFILIACION desc")
	@ResultMap("mapEmpresas")
	public List<EmpresaResumen> buscarHistorialEmpresas(
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin);

	@Select("SELECT	* FROM BN_SATE.BNSATE00_EMPRESA WHERE B00_NUM_RUC =#{ruc}")
	@ResultMap("mapEmpresa")
	public Empresa buscarEmpresa(@Param("ruc") String ruc);

	@Update("UPDATE BN_SATE.BNSATE00_EMPRESA SET B02_REP = #{idRepresentante} WHERE B00_ID_EMP =#{idEmpresa}")
	public void actualizarRepresentanteEmpresa(
			@Param("idEmpresa") Long idEmpresa,
			@Param("idRepresentante") Long idRepresentante);

	@Update("UPDATE bn_sate.bnsate00_empresa set b00_telefono = #{telefonoFijo}, b00_cic = #{cic}, b00_nombre_corto = #{nombreCorto}, b00_tipo = #{tipo}, b00_ubigeo = #{ubigeo}, b00_direccion_mc = #{direccionMc}, b00_direccion = #{direccion} where b00_id_emp = #{id}")
	public void actualizarEmpresa(Empresa empresa);

	@Select("SELECT * FROM BN_SATE.bnsate00_empresa WHERE b02_rep =(SELECT B02_REP FROM bn_sate.bnsate02_usuario WHERE b02_tipo_documento =#{tipoDocumento} AND b02_num_documento =#{numDocumento})")
	@ResultMap("mapEmpresa")
	public Empresa buscarEmpresaPorRepresentante(
			@Param("tipoDocumento") String tipoDocumento,
			@Param("numDocumento") String numDocumento);

	@Select("SELECT * FROM BN_SATE.BNSATE00_EMPRESA emp WHERE emp.B02_REP = (SELECT usu.B02_REP_CREADOR FROM BN_SATE.BNSATE02_USUARIO usu WHERE usu.B02_TIPO_DOCUMENTO = #{tipoDocumento} AND USU.B02_NUM_DOCUMENTO = #{numDocumento})")
	@ResultMap("mapEmpresa")
	public Empresa buscarEmpresaPorUsuario(
			@Param("tipoDocumento") String tipoDocumento,
			@Param("numDocumento") String numDocumento);

	@Select("Select 1 from dual")
	public String probarConexion();

}
