package coop.tecso.examen.exception;

public class AccountMovementValidationException extends AccountMovementException {

	private static final long serialVersionUID = 1L;

	public AccountMovementValidationException() {
		super();
	}

	public AccountMovementValidationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountMovementValidationException(String arg0) {
		super(arg0);
	}

}
