package pe.bn.com.sate.transversal.dto.sate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;

public class Usuario implements Serializable {

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
	private String perfilUsuario;
	private String apellidoMaterno;
	private String apellidoPaterno;

	private String flagCambioClave;

	public Usuario(BnCliente bnCliente, String numeroDocumento,
			String tipoDocumento) {
		this.nombres = (bnCliente.getF02ApePaterno().trim().isEmpty() ? ""
				: bnCliente.getF02ApePaterno().trim()) + " ";
		this.nombres += (bnCliente.getF02ApeMaterno().trim().isEmpty() ? ""
				: bnCliente.getF02ApeMaterno().trim()) + " ";
		this.nombres += (bnCliente.getF02Aprimero().trim().isEmpty() ? ""
				: bnCliente.getF02Aprimero().trim()) + " ";
		this.nombres += (bnCliente.getF02Asegundo().trim().isEmpty() ? ""
				: bnCliente.getF02Asegundo().trim());

		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
	}

	public Usuario(Firmante firmante) {
		for (TipoDocumento tipoDocumentoEnum : Arrays.asList(TipoDocumento
				.values())) {
			if (firmante.getTipoDocumento().equals(
					tipoDocumentoEnum.getCodigoSign()))
				this.tipoDocumento = tipoDocumentoEnum.getCodigoBduc();
		}
		this.numeroDocumento = firmante.getNumeroDocumento();
		this.nombres = firmante.getNombres();
		this.apellidoPaterno = firmante.getApellidoPaterno();
		this.apellidoMaterno = firmante.getApellidoMaterno();
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

	public Usuario() {
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
		this.correoLaboral = correoLaboral.toLowerCase();
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
		this.correoPersonal = correoPersonal.toLowerCase();
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

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public String getFlagCambioClave() {
		return flagCambioClave;
	}

	public void setFlagCambioClave(String flagCambioClave) {
		this.flagCambioClave = flagCambioClave;
	}

}
