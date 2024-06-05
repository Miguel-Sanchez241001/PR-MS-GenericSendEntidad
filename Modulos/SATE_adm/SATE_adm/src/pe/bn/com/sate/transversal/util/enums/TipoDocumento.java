package pe.bn.com.sate.transversal.util.enums;


public enum TipoDocumento {
	DNI("001", "1", "DNI", 8), CARNET_POLICIA_NACIONAL("002", "2",
			"CARNET DE POLICÍA NACIONAL", 9), CARNET_EXTRANJERIA("017", "4",
			"CARNET DE EXTRANJERÍA", 12), PASAPORTTE("005", "5", "PASAPORTE",
			12), RUC("006", "6", "RUC", 11);

	private String codigoSign;
	private String codigoBduc;
	private String nombre;
	private int length;

	private TipoDocumento(String codigoSign, String codigoBduc, String nombre,
			int length) {
		this.codigoSign = codigoSign;
		this.codigoBduc = codigoBduc;
		this.nombre = nombre;
		this.length = length;
	}

	public static String tipoDocumentoBducLetras(String tipoDocumento) {
		if (tipoDocumento != null)
			for (TipoDocumento tipoDocumentoEnum : TipoDocumento.values()) {
				if (tipoDocumento.equals(tipoDocumentoEnum.getCodigoBduc()))
					return tipoDocumentoEnum.getNombre();
			}
		return "";
	}

	public static String tipoDocumentoSignLetras(String tipoDocumento) {
		if (tipoDocumento != null)
			for (TipoDocumento tipoDocumentoEnum : TipoDocumento.values()) {
				if (tipoDocumento.equals(tipoDocumentoEnum.getCodigoSign()))
					return tipoDocumentoEnum.getNombre();
			}
		return "";
	}

	public static int obtenerLengthPorTipo(String tipoDocumento) {
		for (int i = 0; i < TipoDocumento.values().length; i++) {
			if (TipoDocumento.values()[i].getCodigoBduc().equals(tipoDocumento))
				return TipoDocumento.values()[i].getLength();
		}
		return 0;
	}

	public static String obtenerCodigoBduc(String codigoSign) {
		for (TipoDocumento tipoDocumentoEnum : TipoDocumento.values()) {
			if (codigoSign.equals(tipoDocumentoEnum.getCodigoSign()))
				return tipoDocumentoEnum.getCodigoBduc();
		}
		return "";
	}

	public String getCodigoSign() {
		return codigoSign;
	}

	public void setCodigoSign(String codigoSign) {
		this.codigoSign = codigoSign;
	}

	public String getCodigoBduc() {
		return codigoBduc;
	}

	public void setCodigoBduc(String codigoBduc) {
		this.codigoBduc = codigoBduc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
