package vn.com.vhc.alarm.util.exceptions;


public class AL_ParserTimeNodeException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public AL_ParserTimeNodeException(Throwable throwable) {
        super(throwable);
    }

    public AL_ParserTimeNodeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_ParserTimeNodeException(String string) {
        super(string);
    }

    public AL_ParserTimeNodeException() {
        super();
    }
}
