package vn.com.vhc.sts.util.monitor;

import java.util.EventObject;

public class DatabaseStateChangeEvent extends EventObject {
    public static final int NORMAL_STATE = 0;
    public static final int ERROR_STATE = 1;

    private int state; //Current state;
    private String errorMessage;

    public DatabaseStateChangeEvent(Object source) {
        this(source, 0, null);
    }

    public DatabaseStateChangeEvent(Object source, int state) {
        this(source, state, null);
    }

    public DatabaseStateChangeEvent(Object source, int state,
                                    String errorMessage) {
        super(source);
        this.state = state;
        this.errorMessage = errorMessage;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setErrorMessage(String erroMessage) {
        this.errorMessage = erroMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
