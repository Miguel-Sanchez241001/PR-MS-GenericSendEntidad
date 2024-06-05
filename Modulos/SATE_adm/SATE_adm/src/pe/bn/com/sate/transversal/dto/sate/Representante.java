package pe.bn.com.sate.transversal.dto.sate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;

public class Representante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String correoLaboral;
	private String telefonoFijo;
	private String telefonoMovil;
	private String estado;
	private Date fechaRegistro;
	private String codigoTelefono;
	private String anexo;
	private String operadora;
	private String correoPersonal;
	private Long perfilUsuario;
	private String perfilUsuarioDescripcion;
	private String apellidoMaterno;
	private String apellidoPaterno;

	private Long representanteCreador;
	private String flagCambioClave;

	private Long intentosIniciarSesion;
	private Date fechaActualizacionClave;

	public Representante(String tipoDocumento, String numeroDocumento,
			String nombres, String apellidoPaterno, String apellidoMaterno) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.nombres = nombres;
		this.apellidoMaterno = apellidoMaterno;
		this.apellidoPaterno = apellidoPaterno;
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

	public Representante() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCorreoLaboral() {
		return correoLaboral;
	}

	public void setCorreoLaboral(String correoLaboral) {
		this.correoLaboral = correoLaboral;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getCodigoTelefono() {
		return codigoTelefono;
	}

	public void setCodigoTelefono(String codigoTelefono) {
		this.codigoTelefono = codigoTelefono;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getOperadora() {
		return operadora;
	}

	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public String getCorreoPersonal() {
		return correoPersonal;
	}

	public void setCorreoPersonal(String correoPersonal) {
		this.correoPersonal = correoPersonal;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres.toUpperCase();
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno.toUpperCase();
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno.toUpperCase();
	}

	public Long getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(Long perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public String getFlagCambioClave() {
		return flagCambioClave;
	}

	public void setFlagCambioClave(String flagCambioClave) {
		this.flagCambioClave = flagCambioClave;
	}

	public Long getRepresentanteCreador() {
		return representanteCreador;
	}

	public void setRepresentanteCreador(Long representanteCreador) {
		this.representanteCreador = representanteCreador;
	}

	public String getPerfilUsuarioDescripcion() {
		return perfilUsuarioDescripcion;
	}

	public void setPerfilUsuarioDescripcion(String perfilUsuarioDescripcion) {
		this.perfilUsuarioDescripcion = perfilUsuarioDescripcion;
	}

	public Long getIntentosIniciarSesion() {
		return intentosIniciarSesion;
	}

	public void setIntentosIniciarSesion(Long intentosIniciarSesion) {
		this.intentosIniciarSesion = intentosIniciarSesion;
	}

	public Date getFechaActualizacionClave() {
		return fechaActualizacionClave;
	}

	public void setFechaActualizacionClave(Date fechaActualizacionClave) {
		this.fechaActualizacionClave = fechaActualizacionClave;
	}

}
