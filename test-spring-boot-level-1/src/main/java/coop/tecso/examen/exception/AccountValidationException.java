package coop.tecso.examen.exception;

public class AccountValidationException extends AccountException {

	private static final long serialVersionUID = 1L;

	public AccountValidationException() {
		super();
	}

	public AccountValidationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountValidationException(String arg0) {
		super(arg0);
	}

}
