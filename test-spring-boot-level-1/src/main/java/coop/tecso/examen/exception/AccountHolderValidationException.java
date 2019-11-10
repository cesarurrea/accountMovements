package coop.tecso.examen.exception;

public class AccountHolderValidationException extends AccountHolderException {

	private static final long serialVersionUID = 1L;

	public AccountHolderValidationException() {
		super();
	}

	public AccountHolderValidationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountHolderValidationException(String arg0) {
		super(arg0);
	}

}
