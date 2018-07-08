package vn.com.vhc.sts.util.exceptions;


public class STS_ParserTimeNodeException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public STS_ParserTimeNodeException(Throwable throwable) {
        super(throwable);
    }

    public STS_ParserTimeNodeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public STS_ParserTimeNodeException(String string) {
        super(string);
    }

    public STS_ParserTimeNodeException() {
        super();
    }
}
