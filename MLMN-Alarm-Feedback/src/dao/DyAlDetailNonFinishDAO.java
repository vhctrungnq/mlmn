package dao;

import java.util.List;

import vo.CountAlarmLog;
import vo.DyAlDetailNonFinish;
import vo.RAlarmLog;

public interface DyAlDetailNonFinishDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    void insert(DyAlDetailNonFinish record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    void insertSelective(DyAlDetailNonFinish record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    DyAlDetailNonFinish selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    int updateByPrimaryKeySelective(DyAlDetailNonFinish record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_NON_FINISH
     *
     * @ibatorgenerated Mon Dec 09 08:27:47 ICT 2013
     */
    int updateByPrimaryKey(DyAlDetailNonFinish record);
    
    List<CountAlarmLog> getCountForSeverity(String sdate, String edate,
			String bscid, String cellid, String vendor, String district,
			String alarmName, String funtion, String network, String username,
			String province, String team, String alarmType,
			String alarmMappingName,Integer duarationTo,
			String bscPort,String  btsPort,String  objType,Integer quality,String region);

	int countAlarmLog(String sdate, String edate, String bscid, String cellid,
			String vendor, String district, String alarmName, String funtion,
			String severity, String network, String username, String province,
			String team, String alarmType, String alarmMappingName,
			String statusFinish, String strWhere,Integer duarationTo,
			String bscPort,String  btsPort,String  objType,Integer quality,String region);

	List<DyAlDetailNonFinish> getAlarmLog(String sdate, String edate, String bscid,
			String cellid, String vendor, String district, String alarmName,
			String function, String severity, String strWhere, Integer startRecord,
			Integer endRecord, String sortfield, String sortorder, String network,
			String username, String province, String team, String alarmType,
			String alarmMappingName, String statusFinish,Integer duarationTo,
			String bscPort,String  btsPort,String  objType,Integer quality,String region);

}