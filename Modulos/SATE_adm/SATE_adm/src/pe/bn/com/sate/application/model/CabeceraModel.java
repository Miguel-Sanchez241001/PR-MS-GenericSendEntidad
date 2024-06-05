package pe.bn.com.sate.application.model;

import pe.bn.com.sate.transversal.configuration.security.Usuario;

public class CabeceraModel {

	private String fecha;
	private Usuario usuario;

	public CabeceraModel() {
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
