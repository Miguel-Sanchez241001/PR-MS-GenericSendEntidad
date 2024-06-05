package pe.bn.com.sate.application.model;

import java.io.Serializable;

import pe.bn.com.sate.transversal.configuration.security.Usuario;

public class SeguridadModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public SeguridadModel(Usuario usuario){
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
}
