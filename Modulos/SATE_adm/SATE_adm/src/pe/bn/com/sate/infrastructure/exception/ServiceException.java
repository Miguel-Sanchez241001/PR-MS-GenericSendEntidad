package pe.bn.com.sate.infrastructure.exception;

public class ServiceException extends RuntimeException{
	
	public ServiceException() {
        // TODO Auto-generated constructor stub
    }
	public ServiceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

	public ServiceException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
