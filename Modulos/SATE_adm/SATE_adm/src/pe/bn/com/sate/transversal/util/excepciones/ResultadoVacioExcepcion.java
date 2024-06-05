package pe.bn.com.sate.transversal.util.excepciones;

public class ResultadoVacioExcepcion extends Exception{
	public ResultadoVacioExcepcion() {
		// TODO Auto-generated constructor stub
	}

	public ResultadoVacioExcepcion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ResultadoVacioExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
