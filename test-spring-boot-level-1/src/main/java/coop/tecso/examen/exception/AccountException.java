package coop.tecso.examen.exception;

public class AccountException extends DomainException {

	private static final long serialVersionUID = 1L;

	public AccountException() {
		super();
	}

	public AccountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountException(String arg0) {
		super(arg0);
	}

}
