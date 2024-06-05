package pe.bn.com.sate.persistence.exception;

public class PersistenciaExcepcion extends RuntimeException{
	
	public PersistenciaExcepcion() {
        // TODO Auto-generated constructor stub
    }
	public PersistenciaExcepcion(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

	public PersistenciaExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
