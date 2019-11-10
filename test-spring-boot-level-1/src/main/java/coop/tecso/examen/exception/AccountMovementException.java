package coop.tecso.examen.exception;

public class AccountMovementException extends DomainException {

	private static final long serialVersionUID = 1L;

	public AccountMovementException() {
		super();
	}

	public AccountMovementException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountMovementException(String arg0) {
		super(arg0);
	}

}
