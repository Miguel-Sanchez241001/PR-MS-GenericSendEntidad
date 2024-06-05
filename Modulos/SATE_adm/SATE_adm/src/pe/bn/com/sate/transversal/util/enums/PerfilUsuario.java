package pe.bn.com.sate.transversal.util.enums;

public enum PerfilUsuario {

	REPRESENTANTE(1, "Representante"), SOLICITADOR(2, "Solicitador"), AUTORIZADOR(
			3, "Autorizador"), APOYO(4, "Apoyo");

	private int codigo;
	private String descripcion;

	private PerfilUsuario(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public static String perfilUsuarioLetras(int perfilUsuario) {
			for (PerfilUsuario perfilUsuarioEnum : PerfilUsuario.values()) {
				if (perfilUsuario==perfilUsuarioEnum.getCodigo())
					return perfilUsuarioEnum.getDescripcion();
			}
		return "";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
