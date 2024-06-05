package pe.bn.com.sate.transversal.util.enums;

public enum TipoOperacionIG {

	GENERAR_CLAVE("01"), VERIFICAR_CLAVE("02"), CAMBIAR_CLAVE("03"), CAMBIAR_CLAVE_OLVIDO(
			"04");

	private String cod;

	private TipoOperacionIG(String cod) {
		this.cod = cod;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
