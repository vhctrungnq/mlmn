package vn.com.vhc.vmsc2.statistics.domain;

import java.util.Date;

public class ImportLog {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column I_FILE_IMPORT_LOGS.FILE_ID
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    private Long fileId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column I_FILE_IMPORT_LOGS.IMPORT_TIME
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    private Date importTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column I_FILE_IMPORT_LOGS.IMPORT_DURATION
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    private Long importDuration;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column I_FILE_IMPORT_LOGS.STATUS_CODE
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    private String statusCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column I_FILE_IMPORT_LOGS.IMPORTED_RESULT
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    private String importedResult;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column I_FILE_IMPORT_LOGS.FILE_ID
     *
     * @return the value of I_FILE_IMPORT_LOGS.FILE_ID
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column I_FILE_IMPORT_LOGS.FILE_ID
     *
     * @param fileId the value for I_FILE_IMPORT_LOGS.FILE_ID
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column I_FILE_IMPORT_LOGS.IMPORT_TIME
     *
     * @return the value of I_FILE_IMPORT_LOGS.IMPORT_TIME
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public Date getImportTime() {
        return importTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column I_FILE_IMPORT_LOGS.IMPORT_TIME
     *
     * @param importTime the value for I_FILE_IMPORT_LOGS.IMPORT_TIME
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column I_FILE_IMPORT_LOGS.IMPORT_DURATION
     *
     * @return the value of I_FILE_IMPORT_LOGS.IMPORT_DURATION
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public Long getImportDuration() {
        return importDuration;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column I_FILE_IMPORT_LOGS.IMPORT_DURATION
     *
     * @param importDuration the value for I_FILE_IMPORT_LOGS.IMPORT_DURATION
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void setImportDuration(Long importDuration) {
        this.importDuration = importDuration;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column I_FILE_IMPORT_LOGS.STATUS_CODE
     *
     * @return the value of I_FILE_IMPORT_LOGS.STATUS_CODE
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column I_FILE_IMPORT_LOGS.STATUS_CODE
     *
     * @param statusCode the value for I_FILE_IMPORT_LOGS.STATUS_CODE
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column I_FILE_IMPORT_LOGS.IMPORTED_RESULT
     *
     * @return the value of I_FILE_IMPORT_LOGS.IMPORTED_RESULT
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public String getImportedResult() {
        return importedResult;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column I_FILE_IMPORT_LOGS.IMPORTED_RESULT
     *
     * @param importedResult the value for I_FILE_IMPORT_LOGS.IMPORTED_RESULT
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void setImportedResult(String importedResult) {
        this.importedResult = importedResult;
    }
}