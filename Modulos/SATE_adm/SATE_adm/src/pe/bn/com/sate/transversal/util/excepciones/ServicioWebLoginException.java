package pe.bn.com.sate.transversal.util.excepciones;

public class ServicioWebLoginException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicioWebLoginException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public ServicioWebLoginException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
