package pe.bn.com.sate.infrastructure.exception;

public class InternalServiceException extends ServiceException{
	
	public InternalServiceException() {
        // TODO Auto-generated constructor stub
    }
	public InternalServiceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

	public InternalServiceException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
