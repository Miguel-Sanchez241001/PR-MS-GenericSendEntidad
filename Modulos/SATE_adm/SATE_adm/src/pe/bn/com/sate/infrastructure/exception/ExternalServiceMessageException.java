package pe.bn.com.sate.infrastructure.exception;

public class ExternalServiceMessageException extends ServiceException {

	public ExternalServiceMessageException() {
		// TODO Auto-generated constructor stub
	}

	public ExternalServiceMessageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ExternalServiceMessageException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
