package vn.com.vhc.alarm.utils.log;

/**
 * Class use to pass in to log parameter.
 */
public interface AL_DBLogParam {
    /**
     * Subclass must override this method to build insert statement log to database
     * @return insert sql
     */
    public String[] getDMLs();
}
