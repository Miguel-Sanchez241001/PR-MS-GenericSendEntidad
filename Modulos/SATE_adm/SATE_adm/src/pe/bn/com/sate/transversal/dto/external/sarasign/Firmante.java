package pe.bn.com.sate.transversal.dto.external.sarasign;

public class Firmante {

	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;

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

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

}
