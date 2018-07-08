package vn.com.vhc.sts.util.task;

public class STS_IllegalServiceStateException extends Throwable {
	private static final long serialVersionUID = 1L;
	
    public STS_IllegalServiceStateException(Throwable throwable) {
        super(throwable);
    }

    public STS_IllegalServiceStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public STS_IllegalServiceStateException(String string) {
        super(string);
    }

    public STS_IllegalServiceStateException() {
        super();
    }
}
