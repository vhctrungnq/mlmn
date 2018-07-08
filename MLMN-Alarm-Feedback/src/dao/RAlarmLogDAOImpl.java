package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.CountAlarmLog;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;

public class RAlarmLogDAOImpl extends SqlMapClientDaoSupport implements RAlarmLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public RAlarmLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public int deleteByPrimaryKey(String id) {
        RAlarmLog key = new RAlarmLog();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public void insert(RAlarmLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public void insertSelective(RAlarmLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public RAlarmLog selectByPrimaryKey(String id) {
        RAlarmLog key = new RAlarmLog();
        key.setId(id);
        RAlarmLog record = (RAlarmLog) getSqlMapClientTemplate().queryForObject("R_ALARM_LOG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public int updateByPrimaryKeySelective(RAlarmLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG
     *
     * @ibatorgenerated Mon Dec 09 08:30:21 ICT 2013
     */
    public int updateByPrimaryKey(RAlarmLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	@Override
   	public List<RAlarmLog> getAlarmLogAtShift(String netWork, String day,
   			String alarmType, String status, String bsc, String cell) {
   		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_DAY", day);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_STATUS", status);
   		parms.put("P_BSCID", bsc);
   		parms.put("P_CELLID", cell);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getAlarmLogAtShift", parms);
   	}

   /*    @SuppressWarnings("unchecked")
   	@Override
   	public List<RAlarmLog> getAlarmLogSameAtShift(String netWork, String operator,
   			String system, String alarmType, String sdate, String edate) {
       	Map<String, Object> parms = new HashMap<String, Object>();
       	parms.put("P_NETWORK", netWork);
       	parms.put("P_BSCID", operator);
   		parms.put("P_CELLID", system);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_SDATE", sdate);
   		parms.put("P_EDATE", edate);
   		parms.put("P_DATA", null);	
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getAlarmLogSameAtShift", parms);
   	}*/

   	@Override
   	public int updateShiftID(String netWork, String id, String shiftId) {
   		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_NETWORK", netWork);
       	parms.put("P_ID", id);
   		parms.put("P_SHIFT_ID", shiftId);
           int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.updateShiftID", parms);
           return rows;
   	}

   	
   	@SuppressWarnings("unchecked")
   	@Override
	public List<SYS_PARAMETER> titleForm(String function, String network,
			String typeForm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_TYPE", function);
		map.put("P_NETWORK", network);
		map.put("P_FORM", typeForm);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.titleForm", map);
	}

	@Override
	public int deleteAlarmLog(Long id, String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE", function);
       	parms.put("P_ID", id);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG.deleteAlarmLog", parms);
       return rows;
	}

	@Override
	public RAlarmLog selectByPrimaryKey(Long id, String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE", function);
       	parms.put("P_ID", id);
        RAlarmLog record = (RAlarmLog) getSqlMapClientTemplate().queryForObject("R_ALARM_LOG.selectByPrimaryKey", parms);
        return record;
	}

	@Override
	public int insert(RAlarmLog alarmLog, String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE", alarmLog.getSdate());
   		parms.put("P_EDATE", alarmLog.getEdate());
   		parms.put("P_NE", alarmLog.getNe());
   		parms.put("P_SITE", alarmLog.getSite());
   		parms.put("P_CELLID", alarmLog.getCellid());
   		parms.put("P_VENDOR", alarmLog.getVendor());
   		parms.put("P_DISTRICT", alarmLog.getDistrict());
   		parms.put("P_ALARM_TYPE", alarmLog.getAlarmType());
   		parms.put("P_NETWORK", alarmLog.getNetwork());
   		parms.put("P_SEVERITY", alarmLog.getSeverity());
   		parms.put("P_ALARM_INFO", alarmLog.getAlarmInfo());
   		parms.put("P_ALARM_MAPPING_ID", alarmLog.getAlarmMappingId());
   		parms.put("P_ALARM_MAPPING_NAME", alarmLog.getAlarmMappingName());
   		parms.put("P_STATUS_VIEW", alarmLog.getStatusView());
   		parms.put("P_ACK_STATUS", alarmLog.getAckStatus());
   		parms.put("P_ACK_USER", alarmLog.getAckUser());
   		parms.put("P_ACK_TIME", alarmLog.getAckTime());
   		parms.put("P_DISTRICT", alarmLog.getDistrict());
   		parms.put("P_DEPT", alarmLog.getDept());
   		parms.put("P_CAUSEBY", alarmLog.getCauseby());
   		parms.put("P_ACTION_PROCESS", alarmLog.getActionProcess());
   		parms.put("P_CAUSEBY_SYS", alarmLog.getCausebySys());
   		parms.put("P_DESCRIPTION", alarmLog.getDescription());
   		parms.put("P_TEAM", alarmLog.getTeam());
   		parms.put("P_TYPE", function);
   		parms.put("P_SHIFT_ID", alarmLog.getShiftId());
   		parms.put("P_CREATED_BY", alarmLog.getCreatedBy());
   		parms.put("P_CREATE_DATE", alarmLog.getCreateDate());
   		parms.put("P_MD_SDATE", alarmLog.getMdSdate());
           int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.insert", parms);
           return rows;
	}

	@Override
	public int update(RAlarmLog alarmLog, String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_ID", alarmLog.getId());
   		parms.put("P_SDATE", alarmLog.getSdate());
   		parms.put("P_EDATE", alarmLog.getEdate());
   		parms.put("P_NE", alarmLog.getNe());
   		parms.put("P_SITE", alarmLog.getSite());
   		parms.put("P_CELLID", alarmLog.getCellid());
   		parms.put("P_VENDOR", alarmLog.getVendor());
   		parms.put("P_DISTRICT", alarmLog.getDistrict());
   		parms.put("P_ALARM_TYPE", alarmLog.getAlarmType());
   		parms.put("P_NETWORK", alarmLog.getNetwork());
   		parms.put("P_SEVERITY", alarmLog.getSeverity());
   		parms.put("P_ALARM_INFO", alarmLog.getAlarmInfo());
   		parms.put("P_ALARM_MAPPING_ID", alarmLog.getAlarmMappingId());
   		parms.put("P_ALARM_MAPPING_NAME", alarmLog.getAlarmMappingName());
   		parms.put("P_STATUS_VIEW", alarmLog.getStatusView());
   		parms.put("P_ACK_STATUS", alarmLog.getAckStatus());
   		parms.put("P_ACK_USER", alarmLog.getAckUser());
   		parms.put("P_ACK_TIME", alarmLog.getAckTime());
   		parms.put("P_DISTRICT", alarmLog.getDistrict());
   		parms.put("P_DEPT", alarmLog.getDept());
   		parms.put("P_CAUSEBY", alarmLog.getCauseby());
   		parms.put("P_ACTION_PROCESS", alarmLog.getActionProcess());
   		parms.put("P_CAUSEBY_SYS", alarmLog.getCausebySys());
   		parms.put("P_DESCRIPTION", alarmLog.getDescription());
   		parms.put("P_TEAM", alarmLog.getTeam());
   		parms.put("P_TYPE", function);
   		parms.put("P_SHIFT_ID", alarmLog.getShiftId());
   		parms.put("P_MODIFIED_BY", alarmLog.getModifiedBy());
   		parms.put("P_MODIFY_DATE", alarmLog.getModifyDate());
   		parms.put("P_MD_SDATE", alarmLog.getMdSdate());
   		
           int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.update", parms);
           return rows;
	}

	@Override
	public int ackAlarmLog(Long id,Integer shiftID, String function,String userName,String statusEndTime,String etime) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE", function);
       	parms.put("P_ID", id);
   		parms.put("P_SHIFT_ID", shiftID);
   		parms.put("P_MODIFIED_BY", userName);
   		parms.put("P_STATUS_ENDTIME", statusEndTime);
   		parms.put("P_ETIME", etime);
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.ackAlarmLog", parms);
       return rows;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<CountAlarmLog> getCountForSeverity(String sdate, String edate,
			String bscid, String cellid, String vendor, String district,
			String alarmName, String function, String network, String username,
			String province, String team, String alarmType,
			String alarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdate);
   		parms.put("P_SDATE_TO", edate);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", network);
   		parms.put("P_TYPE", function);
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_ID", alarmMappingName);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getCountForSeverity", parms);
	
	}

	@Override
	public int countRAlarmLog(String sdate, String edate, String bscid,
			String cellid, String vendor, String district, String alarmName,
			String function, String severity, String netWork, String username,
			String province, String team, String alarmType,
			String alarmMappingName, String statusFinish, String strWhere) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdate);
   		parms.put("P_SDATE_TO", edate);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_TYPE", function);
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_DATA", null);
   		getSqlMapClientTemplate().queryForObject("R_ALARM_LOG.countRAlarmLog", parms);
          	Integer record = (Integer) parms.get("P_DATA");
          	return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLog> getRAlarmLog(String sdate, String edate,
			String bscid, String cellid, String vendor, String district,
			String alarmName, String function, String severity,
			String strWhere, Integer startRecord, Integer endRecord,
			String sortfield, String sortorder, String netWork,
			String username, String province, String team, String alarmType,
			String alarmMappingName, String statusFinish) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdate);
   		parms.put("P_SDATE_TO", edate);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_START_RECORD", startRecord);
   		parms.put("P_END_RECORD", endRecord);
   		parms.put("P_COLUMN", sortfield);
   		parms.put("P_ORDER", sortorder);
   		parms.put("P_TYPE",function );
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getRAlarmLog", parms);
	}*/

	@Override
	public int unAckAlarmLog(Long id, String function,String userName,String statusEndTime) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE", function);
       	parms.put("P_ID", id);
   		parms.put("P_MODIFIED_BY", userName);
   		parms.put("P_STATUS_ENDTIME", statusEndTime);
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.unAckAlarmLog", parms);
       return rows;
	}

	@Override
	public int countRAlarmLog(String sdateF, String sdateT, String edateF,
			String edateT, String bscid, String cellid, String vendor,
			String district, String alarmName, String function,
			String severity, String netWork, String username, String province,
			String team, String alarmType, String alarmMappingName,
			String statusFinish, String strWhere, String statusView,
			String duaration,String region,String unAlarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdateF);
   		parms.put("P_SDATE_TO", sdateT);
   		parms.put("P_EDATE_FROM", edateF);
   		parms.put("P_EDATE_TO", edateT);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_TYPE", function);
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_STATUS_VIEW", statusView);
   		parms.put("P_DUARATION", duaration);
   		parms.put("P_REGION", region);
   		parms.put("P_UN_ALARM_MAPPING", unAlarmMappingName);
   		parms.put("P_DATA", null);
   		getSqlMapClientTemplate().queryForObject("R_ALARM_LOG.countRAlarmLog", parms);
          	Integer record = (Integer) parms.get("P_DATA");
          	return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLog> getRAlarmLog(String sdateF, String sdateT,
			String edateF, String edateT, String bscid, String cellid,
			String vendor, String district, String alarmName, String function,
			String severity, String strWhere, Integer startRecord,
			Integer endRecord, String sortfield, String sortorder,
			String netWork, String username, String province, String team,
			String alarmType, String alarmMappingName, String statusFinish,
			String statusView, String duaration,String region,String unAlarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdateF);
   		parms.put("P_SDATE_TO", sdateT);
   		parms.put("P_EDATE_FROM", edateF);
   		parms.put("P_EDATE_TO", edateT);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_START_RECORD", startRecord);
   		parms.put("P_END_RECORD", endRecord);
   		parms.put("P_COLUMN", sortfield);
   		parms.put("P_ORDER", sortorder);
   		parms.put("P_TYPE",function );
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_STATUS_VIEW", statusView);
   		parms.put("P_DUARATION", duaration);
   		parms.put("P_REGION", region);
   		parms.put("P_UN_ALARM_MAPPING", unAlarmMappingName);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getRAlarmLog", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountAlarmLog> getCountForSeverity(String sdateF, String sdateT, String edateF,
			String edateT, String bscid, String cellid, String vendor,
			String district, String alarmName, String function,
			String netWork, String username, String province,
			String team, String alarmType, String alarmMappingName,
			String statusFinish, String statusView,
			String duaration,String region,String unAlarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SDATE_FROM", sdateF);
   		parms.put("P_SDATE_TO", sdateT);
   		parms.put("P_EDATE_FROM", edateF);
   		parms.put("P_EDATE_TO", edateT);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_TYPE", function);
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_STATUS_VIEW", statusView);
   		parms.put("P_DUARATION", duaration);
   		parms.put("P_REGION", region);
   		parms.put("P_UN_ALARM_MAPPING", unAlarmMappingName);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getCountForSeverity", parms);
	}
	
	@SuppressWarnings("unchecked")
   	@Override
	public List<RAlarmLog> getRAlarmLogInfoFilter(String siteid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SITEID", siteid);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getRAlarmLogInfoFilter", map);
	}
	//Lay danh sach alarm thong ke trong cham diem ca nhan. status: trang thai xu ly.
	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLog> getAlarmDetailAssess(String dateF, String dateT,
			String catruc, String users, String status, String network,
			String neType, String severity,String columnheader, String strWhere,
			Integer startRecord, Integer endRecord,String sortfield,String sortorder) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", dateF);
   		parms.put("P_SDATE_TO", dateT);
   		parms.put("P_SHIFT", catruc);
   		parms.put("P_USERS", users);
   		parms.put("P_STATUS", status);
   		parms.put("P_NETWORK", network);
   		parms.put("P_NETYPE", neType);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_COLUMNHEADER", columnheader);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_START_RECORD", startRecord);
   		parms.put("P_END_RECORD", endRecord);
   		parms.put("P_COLUMN", sortfield);
   		parms.put("P_ORDER", sortorder);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getAlarmDetailAssess", parms);
	}

	@Override
	public int countAlarmDetailAssess(String dateF, String dateT,
			String catruc, String users, String status,String network,
			String neType, String severity,String columnheader,  String strWhere) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SDATE_FROM", dateF);
   		parms.put("P_SDATE_TO", dateT);
   		parms.put("P_SHIFT", catruc);
   		parms.put("P_USERS", users);
   		parms.put("P_STATUS", status);
   		parms.put("P_NETWORK", network);
   		parms.put("P_NETYPE", neType);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_COLUMNHEADER", columnheader);
   		parms.put("P_WHERE", strWhere);
   		parms.put("P_DATA", null);
   		getSqlMapClientTemplate().queryForObject("R_ALARM_LOG.countAlarmDetailAssess", parms);
          	Integer record = (Integer) parms.get("P_DATA");
          	return record;
	}
	//lay danh sach canh bao moi xuat hien can canh bao am thanh
	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLog> getAlarmAlert(String sdateF, String sdateT,
			String edateF, String edateT, String bscid, String cellid,
			String vendor, String district, String alarmName, String function,
			String severity, String netWork, String username, String province,
			String team, String alarmType, String alarmMappingName,
			String statusFinish, String statusView,
			String region, String unAlarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdateF);
   		parms.put("P_SDATE_TO", sdateT);
   		parms.put("P_EDATE_FROM", edateF);
   		parms.put("P_EDATE_TO", edateT);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_TYPE",function );
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_STATUS_VIEW", statusView);
   		parms.put("P_REGION", region);
   		parms.put("P_UN_ALARM_MAPPING", unAlarmMappingName);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getAlarmAlert", parms);
	}
	
	//lay id canh bao moi xuat hien
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getIdAlarmAlert(String sdateF, String sdateT, String edateF,
			String edateT, String bscid, String cellid, String vendor,
			String district, String alarmName, String function,
			String severity, String netWork, String username, String province,
			String team, String alarmType, String alarmMappingName,
			String statusFinish, String statusView, 
			String region, String unAlarmMappingName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE_FROM", sdateF);
   		parms.put("P_SDATE_TO", sdateT);
   		parms.put("P_EDATE_FROM", edateF);
   		parms.put("P_EDATE_TO", edateT);
   		parms.put("P_NE", bscid);
   		parms.put("P_CELLID", cellid);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_ALARM_NAME", alarmName);
   		parms.put("P_NETWORK", netWork);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_TYPE",function );
   		parms.put("P_USERNAME", username);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_TEAM", team);
   		parms.put("P_ALARM_TYPE", alarmType);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_STATUS_FINISH", statusFinish);
   		parms.put("P_STATUS_VIEW", statusView);
   		parms.put("P_REGION", region);
   		parms.put("P_UN_ALARM_MAPPING", unAlarmMappingName);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getIdAlarmAlert", parms);
	}

	@Override
	public int updateStatusAlerted(String idList, String type) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_ID_ALERTED", idList);
   		parms.put("P_TYPE", type);
   		int rows = getSqlMapClientTemplate().update("R_ALARM_LOG.updateStatusAlerted", parms);
     return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRegion() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getRegion");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDept() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getDept");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLog> getTyLeCanhBao( String sumLevel, String region, String dept, String start,
			String end, String syear, String eyear) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("p_sum_level", sumLevel);
		map.put("p_region", region);
		map.put("p_dept", dept);
		map.put("p_start", start);
		map.put("p_end", end);
		map.put("P_SYEAR", syear);
		map.put("P_Eyear", eyear);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG.getTyLeCanhBao", map);
	}

	

}