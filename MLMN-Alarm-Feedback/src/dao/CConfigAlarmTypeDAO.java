package dao;

import java.util.List;

import vo.CConfigAlarmType;

public interface CConfigAlarmTypeDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    void insert(CConfigAlarmType record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    void insertSelective(CConfigAlarmType record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    CConfigAlarmType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    int updateByPrimaryKeySelective(CConfigAlarmType record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_CONFIG_ALARM_TYPE
     *
     * @ibatorgenerated Thu Oct 24 15:33:35 PDT 2013
     */
    int updateByPrimaryKey(CConfigAlarmType record);

	List<CConfigAlarmType> getAlarmName(String vendor, String alarmType,
			String network);

	List<CConfigAlarmType> getAlarmMappingName(String vendor, String alarmType,
			String network);

	//List<CConfigAlarmType> getAlarmType(String statusView);

	List<CConfigAlarmType> getAlarmTypeByUser(String username,
			String alarmType, String alarmId, String alarmName);

	List<CConfigAlarmType> getCConfigAlarmTypeFilter(String vendor,
			String node, String neType, 
			String blockValue,
			String alarmMappingName,
			String alarmMappingId,
			Integer startRecord, 
			Integer endRecord, 
			String sortfield, 
			String sortorder, 
			String strWhere);

	List<CConfigAlarmType> getCheckUniqueAlarmType(String vendor, String node,
			String neType, String rawTable, String alarmColumnName,
			String alarmValue, String id);

	int updateByUniqueKey(CConfigAlarmType record);

	List<CConfigAlarmType> getAlarmMappingNameList();

	List<CConfigAlarmType> getAlarmMappingIdList();

	List<CConfigAlarmType> getDistinctAlarmTypeList();

	int countCConfigAlarmType(String vendor, String node, String neType,
			String blockValue, String alarmMappingName, String alarmMappingId,
			String strWhere);

	List<CConfigAlarmType> getAlarmType(String network, String  statusView);
}