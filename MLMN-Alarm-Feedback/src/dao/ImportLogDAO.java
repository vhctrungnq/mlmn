package dao;

import java.util.List;

import vo.ImportLog;
import vo.SYS_PARAMETER;

public interface ImportLogDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_IMPORT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    void insert(ImportLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_IMPORT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    void insertSelective(ImportLog record);

	List<ImportLog> filter(String startDate, String endDate, String statusCode,String fileName,
			int startRecord, int endRecord, String column, int order);

	Integer countRow(String startDate, String endDate, String statusCode,String fileName);

	List<SYS_PARAMETER> titleImportLog();


}