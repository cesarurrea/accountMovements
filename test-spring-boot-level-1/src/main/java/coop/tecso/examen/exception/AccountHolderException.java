package coop.tecso.examen.exception;

public class AccountHolderException extends DomainException {

	private static final long serialVersionUID = 1L;

	public AccountHolderException() {
		super();
	}

	public AccountHolderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountHolderException(String arg0) {
		super(arg0);
	}

}
