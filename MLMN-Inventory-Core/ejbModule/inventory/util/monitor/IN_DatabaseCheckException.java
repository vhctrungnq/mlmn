package inventory.util.monitor;

public class IN_DatabaseCheckException extends Exception {
    public IN_DatabaseCheckException(Throwable throwable) {
        super(throwable);
    }

    public IN_DatabaseCheckException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IN_DatabaseCheckException(String string) {
        super(string);
    }

    public IN_DatabaseCheckException() {
        super();
    }
}
