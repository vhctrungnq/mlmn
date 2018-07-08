package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.RAlarmLogTemp;

public class RAlarmLogTempDAOImpl extends SqlMapClientDaoSupport implements RAlarmLogTempDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG_TEMP
     *
     * @ibatorgenerated Tue Oct 15 14:20:29 ICT 2013
     */
    public RAlarmLogTempDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG_TEMP
     *
     * @ibatorgenerated Tue Oct 15 14:20:29 ICT 2013
     */
    public void insert(RAlarmLogTemp record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOG_TEMP.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOG_TEMP
     *
     * @ibatorgenerated Tue Oct 15 14:20:29 ICT 2013
     */
    public void insertSelective(RAlarmLogTemp record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOG_TEMP.ibatorgenerated_insertSelective", record);
    }
	public int insertToTemp(RAlarmLogTemp alarmLog,String tableName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_SDATE", alarmLog.getSdate());
   		parms.put("P_EDATE", alarmLog.getEdate());
   		parms.put("P_NE", alarmLog.getNe());
   		parms.put("P_SITE", alarmLog.getSite());
   		parms.put("P_CELLID", alarmLog.getCellid());
   		parms.put("P_MO", alarmLog.getMo());
   		parms.put("P_DIP", alarmLog.getDip());
   		parms.put("P_VENDOR", alarmLog.getVendor());
   		parms.put("P_ALARM_TYPE", alarmLog.getAlarmType());
   		parms.put("P_SEVERITY", alarmLog.getSeverity());
   		parms.put("P_ALARM_NAME", alarmLog.getAlarmName());
   		parms.put("P_ALARM_INFO", alarmLog.getAlarmInfo());
   		parms.put("P_ALARM_ID", alarmLog.getAlarmId());
   		parms.put("P_ALARM_MAPPING_ID", alarmLog.getAlarmMappingId());
   		parms.put("P_ALARM_MAPPING_NAME", alarmLog.getAlarmMappingName());
   		parms.put("P_STATUS_VIEW", alarmLog.getStatusView());
   		parms.put("P_ACK_STATUS", alarmLog.getAckStatus());
   		parms.put("P_ACK_USER", alarmLog.getAckUser());
   		parms.put("P_ACK_TIME", alarmLog.getAckTime());
   		parms.put("P_SHIFT_ID", alarmLog.getShiftId());
   		parms.put("P_CAUSEBY", alarmLog.getCauseby());
   		parms.put("P_NETWORK", alarmLog.getNetwork());
   		parms.put("P_BSCPORT", alarmLog.getBscport());
   		parms.put("P_BTSPORT", alarmLog.getBtsport());
   		parms.put("P_NOTE", alarmLog.getNote());
   		parms.put("P_NE_TYPE", alarmLog.getNeType());
   		parms.put("P_REP_COUNT", alarmLog.getRepCount());
   		parms.put("P_CH_TYPE", alarmLog.getChType());
   		parms.put("P_OBJECT_REFERENCE", alarmLog.getObjectReference());
   		parms.put("P_SLOT", alarmLog.getSlot());
   		parms.put("P_FM_ALARMID", alarmLog.getFmAlarmid());
   		parms.put("P_OBJ_TYPE", alarmLog.getObjType());
   		parms.put("P_CLR_STATUS", alarmLog.getClrStatus());
   		parms.put("P_EVTTYPE", alarmLog.getEvttype());
   		parms.put("P_RACK", alarmLog.getRack());
   		parms.put("P_SUBRACK", alarmLog.getSubrack());
   		parms.put("P_USERNAME", alarmLog.getUsername());
   		parms.put("P_TABLE_NAME",tableName);
           int rows = getSqlMapClientTemplate().update("R_ALARM_LOG_TEMP.insertToTemp", parms);
           return rows;
	}
    @Override
	public int deleteAlarmLogTemp(String username, String vendor) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG_TEMP.deleteAlarmLogTemp", parms);
       return rows;
	}

	/*@Override
	public int insertHistoryToRAlarmLog(String username, String vendor) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG_TEMP.insertHistoryToRAlarmLog", parms);
       return rows;
	}
*/
	@Override
	public int insertActiveToRAlarmLog(String username, String vendor,String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       	parms.put("P_TYPE_FUNTION", function);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG_TEMP.insertActiveToRAlarmLog", parms);
       return rows;
	}

/*	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLogTemp> getAlarmLog(String vendor, String username, String status) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       	parms.put("P_STATUS", status);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG_TEMP.getAlarmLog", parms);
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public RAlarmLogTemp getInformaionInsert(String vendor, String username) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
   		parms.put("P_DATA", null);
   		RAlarmLogTemp record = null;
   		List<RAlarmLogTemp> list= getSqlMapClientTemplate().queryForList("R_ALARM_LOG_TEMP.getInformaionInsert", parms);
   		if (list.size()>0)
   		{
   			record = list.get(0);
   		}
   		return record;
	}

	@Override
	public void insertNokiaTemp(RAlarmLogTemp record) {
		  getSqlMapClientTemplate().insert("R_ALARM_LOG_TEMP.insertNokiaTemp", record);
    }
	@Override
	public void insertNokiaTempUpload(RAlarmLogTemp record) {
		  getSqlMapClientTemplate().insert("R_ALARM_LOG_TEMP.insertNokiaTempUpload", record);
    }
	@Override
	public int insertNokia(String vendor, String username) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG_TEMP.insertNokia", parms);
       return rows;
	}

	@Override
	public int deleteAlarmLogNSNTemp(String userName) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", userName);
       int rows = getSqlMapClientTemplate().delete("R_ALARM_LOG_TEMP.deleteAlarmLogNSNTemp", parms);
       return rows;
	}

	

	@Override
	public int updateAll(String username, String vendor) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       int rows = getSqlMapClientTemplate().update("R_ALARM_LOG_TEMP.updateAll", parms);
       return rows;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLogTemp> getAlarmLog(String vendor, String username,
			String status) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_VENDOR", vendor);
       	parms.put("P_USERNAME", username);
       	parms.put("P_STATUS", status);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG_TEMP.getAlarmLog", parms);
	}
	// Lay danh sach alarm co trong file se duoc insert hoac update
	//status:aLL( lAY TAT CA CAC ALARM CO TRONG FILE DUOC INSERT/UPDATE, U: DANH SACH CAC CANH BAO KHONG CO TRONG FILE 
	@SuppressWarnings("unchecked")
	@Override
	public List<RAlarmLogTemp> getAlarmLogTemp(String vendor, String username,
			String status) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_VENDOR", vendor);
       	parms.put("P_USERNAME", username);
       	parms.put("P_STATUS", status);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("R_ALARM_LOG_TEMP.getAlarmLogTemp", parms);
	}

	@Override
	public int updateEdate(String username, String vendor, String network,String etime, String causebyFinished) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
    	parms.put("P_NETWORK", network);
    	parms.put("P_ETIME", etime);
    	parms.put("P_CAUSE_FINISHED", causebyFinished);
    	parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("R_ALARM_LOG_TEMP.updateEdate", parms);
      	Integer record = (Integer) parms.get("P_DATA");
      	return record;
	}
	
	@Override
	public int synAlarmHistory(String username, String vendor) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
       int rows = getSqlMapClientTemplate().update("R_ALARM_LOG_TEMP.synAlarmHistory", parms);
       return rows;
	}
	@Override
	public int synAlarmActive(String username, String vendor, String network,String etime) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_VENDOR", vendor);
    	parms.put("P_NETWORK", network);
    	parms.put("P_ETIME", etime);
    	parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("R_ALARM_LOG_TEMP.synAlarmActive", parms);
      	Integer record = (Integer) parms.get("P_DATA");
      	return record;
	}


}