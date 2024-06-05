package pe.bn.com.sate.infrastructure.exception;

public class ExternalServiceSaraSignException extends ServiceException{
	
	public ExternalServiceSaraSignException() {
        // TODO Auto-generated constructor stub
    }
	public ExternalServiceSaraSignException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

	public ExternalServiceSaraSignException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
