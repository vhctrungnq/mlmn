package vn.com.vhc.alarm.util.monitor;

@SuppressWarnings("serial")
public class AL_DatabaseCheckException extends Exception {
    public AL_DatabaseCheckException(Throwable throwable) {
        super(throwable);
    }

    public AL_DatabaseCheckException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_DatabaseCheckException(String string) {
        super(string);
    }

    public AL_DatabaseCheckException() {
        super();
    }
}
