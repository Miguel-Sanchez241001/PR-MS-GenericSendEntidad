package pe.bn.com.sate.transversal.util.enums;

public enum TipoEstadoUsuario {

	ACTIVO("1", "ACTIVO"), INACTIVO("0", "INACTIVO");

	private String id;
	private String descripcion;

	private TipoEstadoUsuario(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
