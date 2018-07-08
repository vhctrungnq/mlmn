package inventory.util.exceptions;

public class IN_ConfigDbFailException extends Exception {
	private static final long serialVersionUID = 1L;

	public IN_ConfigDbFailException(Throwable throwable) {
        super(throwable);
    }

    public IN_ConfigDbFailException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IN_ConfigDbFailException(String string) {
        super(string);
    }

    public IN_ConfigDbFailException() {
        super();
    }
}
