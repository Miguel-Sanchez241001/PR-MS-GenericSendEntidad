package pe.bn.com.sate.transversal.util.enums;

public enum TipoReporte {
	REPORTE_SOLICITUD_AFILIACION("reporteSolicitudAfiliacion", "Afiliacion"), REPORTE_SOLICITUD_DESAFILIACION(
			"reporteSolicitudDesafiliacion", "Desafiliacion");

	String nombreReporte;
	String nombreArchivo;

	private TipoReporte(String nombreReporte, String nombreReporteExcel) {
		this.nombreReporte = nombreReporte;
		this.nombreArchivo = nombreReporteExcel;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

}
