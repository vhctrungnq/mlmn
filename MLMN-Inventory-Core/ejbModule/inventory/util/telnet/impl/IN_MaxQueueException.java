package inventory.util.telnet.impl;

public class IN_MaxQueueException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public IN_MaxQueueException(Throwable throwable) {
        super(throwable);
    }

    public IN_MaxQueueException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IN_MaxQueueException(String string) {
        super(string);
    }

    public IN_MaxQueueException() {
        super();
    }
}
