package pe.bn.com.sate.application.model;

import java.io.Serializable;
import java.util.List;

import org.omnifaces.el.functions.Arrays;

import pe.bn.com.sate.transversal.util.enums.TipoDocumento;

public class LoginModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ruc;
	private String tipoDocumento;
	private String numeroDocumento;
	private String clave;
	private List<TipoDocumento> listaTipoDocumento;

	public LoginModel(){
	}
	
	
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
