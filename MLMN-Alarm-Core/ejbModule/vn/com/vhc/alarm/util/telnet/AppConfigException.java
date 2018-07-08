package vn.com.vhc.alarm.util.telnet;

public class AppConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppConfigException() {
		super();
	}
	
	public AppConfigException(String message) {
		super(message);
	}
	
	public AppConfigException(Throwable throwable) {
		super(throwable);
	}
	
	public AppConfigException (String message, Throwable throwable) {
		super(message, throwable);
	}

}
