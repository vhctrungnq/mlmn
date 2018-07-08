package inventory.util.exceptions;


public class IN_ParserTimeNodeException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public IN_ParserTimeNodeException(Throwable throwable) {
        super(throwable);
    }

    public IN_ParserTimeNodeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IN_ParserTimeNodeException(String string) {
        super(string);
    }

    public IN_ParserTimeNodeException() {
        super();
    }
}
