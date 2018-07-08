package vn.com.vhc.sts.util.monitor;

public class DatabaseCheckException extends Exception {
    public DatabaseCheckException(Throwable throwable) {
        super(throwable);
    }

    public DatabaseCheckException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DatabaseCheckException(String string) {
        super(string);
    }

    public DatabaseCheckException() {
        super();
    }
}
