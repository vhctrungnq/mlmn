package dao;

import java.util.List;

import vo.RAlarmOperationLog;

public interface RAlarmOperationLogDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    void insert(RAlarmOperationLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    void insertSelective(RAlarmOperationLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    RAlarmOperationLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    int updateByPrimaryKeySelective(RAlarmOperationLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    int updateByPrimaryKey(RAlarmOperationLog record);

	List<RAlarmOperationLog> getOperationLogDetail(String startDate, String endDate,
			String vendor, String neType, String neName, String user,
			Integer startRecord, Integer endRecord, String sortfield,
			String sortorder, String strWhere);

	int countOperationLogDetail(String startDate, String endDate,
			String vendor, String neType, String neName, String user,
			String strWhere);

	List<RAlarmOperationLog> getOperationLogTotal(String startDate,
			String endDate, String vendor, String neType, String neName,
			String user, Integer startRecord, Integer endRecord,
			String sortfield, String sortorder, String strWhere);

	int countOperationLogTotal(String startDate, String endDate, String vendor,
			String neType, String neName, String user, String strWhere);

	List<RAlarmOperationLog> getUserForOperationLog();
}