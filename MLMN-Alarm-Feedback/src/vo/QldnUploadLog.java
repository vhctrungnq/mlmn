package vo;

import java.io.Serializable;
import java.util.Date;

public class QldnUploadLog implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column QLDN_UPLOAD_LOG.LOAI_FILE
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private String loaiFile;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column QLDN_UPLOAD_LOG.TG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private Date tgUpload;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column QLDN_UPLOAD_LOG.NGUOI_NHAP
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private String nguoiNhap;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column QLDN_UPLOAD_LOG.FILE_PATH
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private String filePath;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column QLDN_UPLOAD_LOG.LOG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private String logUpload;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table QLDN_UPLOAD_LOG
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column QLDN_UPLOAD_LOG.LOAI_FILE
     *
     * @return the value of QLDN_UPLOAD_LOG.LOAI_FILE
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public String getLoaiFile() {
        return loaiFile;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column QLDN_UPLOAD_LOG.LOAI_FILE
     *
     * @param loaiFile the value for QLDN_UPLOAD_LOG.LOAI_FILE
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public void setLoaiFile(String loaiFile) {
        this.loaiFile = loaiFile;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column QLDN_UPLOAD_LOG.TG_UPLOAD
     *
     * @return the value of QLDN_UPLOAD_LOG.TG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public Date getTgUpload() {
        return tgUpload;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column QLDN_UPLOAD_LOG.TG_UPLOAD
     *
     * @param tgUpload the value for QLDN_UPLOAD_LOG.TG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public void setTgUpload(Date tgUpload) {
        this.tgUpload = tgUpload;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column QLDN_UPLOAD_LOG.NGUOI_NHAP
     *
     * @return the value of QLDN_UPLOAD_LOG.NGUOI_NHAP
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public String getNguoiNhap() {
        return nguoiNhap;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column QLDN_UPLOAD_LOG.NGUOI_NHAP
     *
     * @param nguoiNhap the value for QLDN_UPLOAD_LOG.NGUOI_NHAP
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public void setNguoiNhap(String nguoiNhap) {
        this.nguoiNhap = nguoiNhap;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column QLDN_UPLOAD_LOG.FILE_PATH
     *
     * @return the value of QLDN_UPLOAD_LOG.FILE_PATH
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column QLDN_UPLOAD_LOG.FILE_PATH
     *
     * @param filePath the value for QLDN_UPLOAD_LOG.FILE_PATH
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column QLDN_UPLOAD_LOG.LOG_UPLOAD
     *
     * @return the value of QLDN_UPLOAD_LOG.LOG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public String getLogUpload() {
        return logUpload;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column QLDN_UPLOAD_LOG.LOG_UPLOAD
     *
     * @param logUpload the value for QLDN_UPLOAD_LOG.LOG_UPLOAD
     *
     * @ibatorgenerated Tue Jul 04 14:30:14 ICT 2017
     */
    public void setLogUpload(String logUpload) {
        this.logUpload = logUpload;
    }
}