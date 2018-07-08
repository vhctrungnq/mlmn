package inventory.util.task;

public class IN_IllegalServiceStateException extends Throwable {
	private static final long serialVersionUID = 1L;
	
    public IN_IllegalServiceStateException(Throwable throwable) {
        super(throwable);
    }

    public IN_IllegalServiceStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IN_IllegalServiceStateException(String string) {
        super(string);
    }

    public IN_IllegalServiceStateException() {
        super();
    }
}
