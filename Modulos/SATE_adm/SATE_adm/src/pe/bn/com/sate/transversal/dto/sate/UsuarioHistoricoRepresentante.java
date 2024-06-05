package pe.bn.com.sate.transversal.dto.sate;

import java.io.Serializable;
import java.util.Date;

public class UsuarioHistoricoRepresentante implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idUsuario;
	private Long idRepresentante;
	private Long usuarioPerfil;
	private String usuarioPerfilDescripcion;
	private String correoLaboral;
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Date fechaActivacion;
	private Date fechaDesactivacion;

	public UsuarioHistoricoRepresentante() {
	}

	public UsuarioHistoricoRepresentante(Long id, Date fechaDesactivacion) {
		this.id = id;
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public UsuarioHistoricoRepresentante(Long idRepresentante, Long idUsuario,
			Long usuarioPerfil, Date fechaActivacion) {
		this.idRepresentante = idRepresentante;
		this.idUsuario = idUsuario;
		this.usuarioPerfil = usuarioPerfil;
		this.fechaActivacion = fechaActivacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getCorreoLaboral() {
		return correoLaboral;
	}

	public void setCorreoLaboral(String correoLaboral) {
		this.correoLaboral = correoLaboral;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaDesactivacion() {
		return fechaDesactivacion;
	}

	public void setFechaDesactivacion(Date fechaDesactivacion) {
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public String nombreCompleto() {
		String nombreCompleto = "";

		if (nombres != null)
			nombreCompleto += nombres;
		if (apellidoPaterno != null)
			nombreCompleto += " " + apellidoPaterno;
		if (apellidoMaterno != null)
			nombreCompleto += " " + apellidoMaterno;

		return nombreCompleto;

	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public Long getUsuarioPerfil() {
		return usuarioPerfil;
	}

	public void setUsuarioPerfil(Long usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	public String getUsuarioPerfilDescripcion() {
		return usuarioPerfilDescripcion;
	}

	public void setUsuarioPerfilDescripcion(String usuarioPerfilDescripcion) {
		this.usuarioPerfilDescripcion = usuarioPerfilDescripcion;
	}

}
