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

import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.RepresentanteHistoricoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;

//@Transactional(value="sate",propagation = Propagation.MANDATORY)
public interface RepresentanteMapper {

	@Insert("INSERT INTO BN_SATE.BNSATE02_USUARIO (B02_TIPO_DOCUMENTO,B02_NUM_DOCUMENTO,B02_NOMBRES,B02_CORREO,B02_TELEFONO_FIJO,B02_TELEFONO_MOVIL,B02_ESTADO,B02_FEC_REGISTRO,B02_COD_TELEFONO,B02_ANEXO,B02_INTENTOS_INIC_SESION,B02_FECHA_ACT_CLAVE,B02_OPERADORA,B02_CORREO_PERSONAL,B02_PERFIL_USUARIO,B02_APPATERNO,B02_APMATERNO, B02_FLAG_CAMBIO_CLAVE) "
			+ "values (#{tipoDocumento}, "
			+ "#{numeroDocumento},#{nombres}, "
			+ "#{correoLaboral},#{telefonoFijo},#{telefonoMovil},#{estado},#{fechaRegistro}, "
			+ "#{codigoTelefono}, "
			+ "#{anexo}, #{intentosIniciarSesion}, #{fechaActualizacionClave}, "
			+ "#{operadora},#{correoPersonal},#{perfilUsuario},#{apellidoPaterno},#{apellidoMaterno},#{flagCambioClave})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "B02_REP")
	public void registrarRepresentante(Representante representante);

	@Update("UPDATE BN_SATE.BNSATE02_USUARIO "
			+ "SET B02_TIPO_DOCUMENTO=#{tipoDocumento}, "
			+ "B02_NUM_DOCUMENTO=#{numeroDocumento}, "
			+ "B02_NOMBRES=#{nombres}, "
			+ "B02_CORREO=#{correoLaboral}, "
			+ "B02_APPATERNO=#{apellidoPaterno}, B02_APMATERNO=#{apellidoMaterno}, "
			+ "B02_TELEFONO_FIJO=#{telefonoFijo}, "
			+ "B02_TELEFONO_MOVIL=#{telefonoMovil}, "
			+ "B02_COD_TELEFONO=#{codigoTelefono}, "
			+ "B02_ANEXO=#{anexo}, "
			+ "B02_OPERADORA=#{operadora}, B02_INTENTOS_INIC_SESION=#{intentosIniciarSesion}, B02_FECHA_ACT_CLAVE=#{fechaActualizacionClave}, "
			+ "B02_REP_CREADOR=#{representanteCreador}, B02_ESTADO=#{estado}, "
			+ "B02_CORREO_PERSONAL=#{correoPersonal}, "
			+ "B02_FLAG_CAMBIO_CLAVE=#{flagCambioClave} "
			+ "WHERE B02_REP=#{id}")
	public void actualizarRepresentante(Representante representante);

	@Insert("INSERT INTO BN_SATE.BNSATE03_REP_EMP (B00_ID_EMP,B02_REP,B03_FEC_REGISTRO) VALUES (#{idEmpresa},#{idRepresentante},#{fechaRegistro})")
	public void registrarRepresentanteHistoricoEmpresa(
			RepresentanteHistoricoEmpresa representanteHistoricoEmpresa);

	@Select("select bn03.b03_fec_registro,bn02.*, case when (bn00.b02_rep= bn02.b02_rep and bn03.b03_fec_registro = (select max(b03_fec_registro) from BN_SATE.bnsate03_rep_emp where b00_id_emp= #{idEmpresa})) then 'ACTIVO' else 'INACTIVO' end as B02_ESTADO_REP "
			+ "from bn_sate.bnsate00_empresa bn00 "
			+ "INNER JOIN bn_sate.bnsate03_rep_emp bn03 on bn03.b00_id_emp= bn00.b00_id_emp "
			+ "INNER JOIN bn_sate.BNSATE02_USUARIO bn02 on bn03.b02_rep=bn02.b02_rep "
			+ "WHERE bn00.b00_id_emp= #{idEmpresa} AND trunc(bn03.b03_fec_registro)  BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy')) order by b03_fec_registro desc")
	@ResultMap("mapHistorialRepresentante")
	public List<Representante> buscarHistorialRepresentantes(
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin,
			@Param("idEmpresa") Long idEmpresa);

	@Select("select distinct bn02.*, case when bn00.b02_rep= bn02.b02_rep then 'ACTIVO' else 'INACTIVO' end as B02_ESTADO_REP "
			+ "from bn_sate.bnsate00_empresa bn00 "
			+ "INNER JOIN bn_sate.bnsate03_rep_emp bn03 on bn03.b00_id_emp= bn00.b00_id_emp  "
			+ "INNER JOIN bn_sate.BNSATE02_USUARIO bn02 on bn03.b02_rep=bn02.b02_rep "
			+ "WHERE bn00.b00_id_emp= #{idEmpresa} AND TRUNC(bn03.b03_fec_registro)  BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy'))")
	@ResultMap("mapRepresentante")
	public List<Representante> buscarRepresentantes(
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin,
			@Param("idEmpresa") Long idEmpresa);

	@Select("SELECT * FROM BN_SATE.BNSATE02_USUARIO WHERE B02_TIPO_DOCUMENTO = #{tipoDocumento} AND B02_NUM_DOCUMENTO=#{numDocumento} AND B02_PERFIL_USUARIO = 1")
	@ResultMap("mapRepresentante")
	public Representante buscarRepresentante(
			@Param("numDocumento") String numDocumento,
			@Param("tipoDocumento") String tipoDocumento);

	@Select("SELECT usu.*, rol.b11_descripcion as B11_perfil_usuario_descripcion FROM BN_SATE.BNSATE02_USUARIO usu JOIN BN_SATE.bnsate11_roles rol on usu.b02_perfil_usuario = rol.b11_id_rol WHERE usu.B02_TIPO_DOCUMENTO = #{tipoDocumento} AND usu.B02_NUM_DOCUMENTO=#{numDocumento}")
	@ResultMap("mapRepresentante")
	public Representante buscarUsuario(
			@Param("numDocumento") String numDocumento,
			@Param("tipoDocumento") String tipoDocumento);

	@Select("SELECT * FROM bn_sate.bnsate02_usuario WHERE B02_REP =(SELECT B02_REP FROM BN_SATE.bnsate00_empresa where B00_ID_EMP =#{idEmpresa})")
	@ResultMap("mapRepresentante")
	public Representante obtenerRepresentanteActual(
			@Param("idEmpresa") Long idEmpresa);

	@Select("SELECT usr.B08_ID_USU_REP, usr.B02_ID_REP, usr.B02_ID_USU, usu.b02_correo, (select rol.b11_descripcion from bn_sate.bnsate11_roles rol where rol.b11_id_rol = usr.b08_usu_perfil) as B11_USU_PERFIL_DESCRIPCION, usu.B02_TIPO_DOCUMENTO, usu.B02_NUM_DOCUMENTO, usu.B02_NOMBRES, usu.B02_APPATERNO, usu.B02_APMATERNO, usr.b08_fecha_activacion, usr.b08_fecha_desactivacion "
			+ "FROM bn_sate.bnsate08_usu_rep usr join BN_SATE.bnsate02_usuario usu ON usr.b02_id_usu = usu.b02_rep "
			+ "WHERE usr.b02_id_rep = #{idRepresentante} AND  (TRUNC(usr.b08_fecha_activacion) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy')) "
			+ "OR TRUNC(usr.b08_fecha_desactivacion) BETWEEN TRUNC(To_Date(#{fechaInicio},'dd/mm/yy')) AND TRUNC(To_Date(#{fechaFin},'dd/mm/yy')))")
	@ResultMap("mapUsuarioHistorico")
	public List<UsuarioHistoricoRepresentante> buscarUsuarios(
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin,
			@Param("idRepresentante") Long idRepresentante);

	@Select("select usu.* from bn_sate.bnsate00_empresa emp "
			+ "join bn_sate.bnsate02_usuario usu on (emp.b02_rep = usu.b02_rep OR emp.b02_rep = usu.b02_rep_creador) "
			+ "where emp.b00_id_emp = #{idEmpresa}")
	@ResultMap("mapRepresentante")
	public List<Representante> buscarUsuariosDeEmpresa(
			@Param("idEmpresa") Long idEmpresa);

	@Select("select * from bn_sate.bnsate02_usuario where b02_rep_creador = #{idRepresentante}")
	@ResultMap("mapRepresentante")
	public List<Representante> buscarUsuariosDeRepresentante(
			@Param("idRepresentante") Long idRepresentante);

	@Update("UPDATE BN_SATE.BNSATE02_USUARIO SET B02_ESTADO='0' WHERE B02_REP=#{idUsuario}")
	public void desactivarUsuario(Long idUsuario);

	@Update("UPDATE BN_SATE.BNSATE02_USUARIO SET B02_REP_CREADOR=#{idRepresentante} WHERE B02_REP=#{idUsuario}")
	public void actualizarRepresentanteUsuario(
			@Param("idRepresentante") Long idRepresentante,
			@Param("idUsuario") Long idUsuario);

	@Update("UPDATE BN_SATE.BNSATE08_USU_REP SET B08_FECHA_DESACTIVACION = #{fechaDesactivacion} where B08_ID_USU_REP=#{id}")
	public void DesactivarUsuarioHistoricoRepresentante(
			UsuarioHistoricoRepresentante usuarioHistoricoRepresentante);

	@Insert("INSERT INTO BN_SATE.BNSATE08_USU_REP(B02_ID_REP, B02_ID_USU, B08_USU_PERFIL, B08_FECHA_ACTIVACION) VALUES (#{idRepresentante},#{idUsuario},#{usuarioPerfil},#{fechaActivacion})")
	public void registrarUsuarioHistoricoRepresentante(
			UsuarioHistoricoRepresentante usuarioHistoricoRepresentante);

	@Select("SELECT * FROM BN_SATE.bnsate08_usu_rep WHERE b08_fecha_desactivacion IS NULL AND b02_id_rep = #{idRepresentante} AND b02_id_usu = #{idUsuario} "
			+ "AND b08_fecha_activacion = (SELECT MAX(b08_fecha_activacion) FROM BN_SATE.bnsate08_usu_rep WHERE b08_fecha_desactivacion is null "
			+ "AND b02_id_rep = #{idRepresentante} AND b02_id_usu = #{idUsuario})")
	@ResultMap("mapUsuarioHistorico")
	public UsuarioHistoricoRepresentante buscarUsuarioHistoricoRepresentante(
			@Param("idRepresentante") Long idRepresentante,
			@Param("idUsuario") Long idUsuario);

	@Select("SELECT COUNT(*) FROM BN_SATE.bnsate02_usuario WHERE b02_correo = #{correo} AND (b02_tipo_documento != #{tipoDocumento} OR b02_num_documento != #{numeroDocumento})")
	public Long existeCorreoLaboral(@Param("correo") String correo,
			@Param("tipoDocumento") String tipoDocumento,
			@Param("numeroDocumento") String numeroDocumento);

	@Select("SELECT COUNT(*) FROM BN_SATE.bnsate02_usuario WHERE b02_telefono_movil = #{telefonoMovil} AND (b02_tipo_documento != #{tipoDocumento} OR b02_num_documento != #{numeroDocumento})")
	public Long existeTelefonoMovil(
			@Param("telefonoMovil") String telefonoMovil,
			@Param("tipoDocumento") String tipoDocumento,
			@Param("numeroDocumento") String numeroDocumento);
}
