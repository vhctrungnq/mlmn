package vn.com.vhc.sts.utils.log;

/**
 * Class use to pass in to log parameter.
 */
public interface STS_DBLogParam {
    /**
     * Subclass must override this method to build insert statement log to database
     * @return insert sql
     */
    public String[] getDMLs();
}