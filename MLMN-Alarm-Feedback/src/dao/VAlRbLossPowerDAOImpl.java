package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.DetailLostConfig;
import vo.SYS_PARAMETER;
import vo.VAlRbLossPower;

public class VAlRbLossPowerDAOImpl extends SqlMapClientDaoSupport implements VAlRbLossPowerDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_POWER
     *
     * @ibatorgenerated Thu Nov 29 10:02:36 ICT 2012
     */
    public VAlRbLossPowerDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_POWER
     *
     * @ibatorgenerated Thu Nov 29 10:02:36 ICT 2012
     */
    public void insert(VAlRbLossPower record) {
        getSqlMapClientTemplate().insert("V_AL_RB_LOSS_POWER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_POWER
     *
     * @ibatorgenerated Thu Nov 29 10:02:36 ICT 2012
     */
    public void insertSelective(VAlRbLossPower record) {
        getSqlMapClientTemplate().insert("V_AL_RB_LOSS_POWER.ibatorgenerated_insertSelective", record);
    }
    @Override
	public int updateByPrimaryKeySelective(VAlRbLossPower record) {
		 int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_POWER.updateByPrimaryKeySelective", record);
	        return rows;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
        Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", id);
		int rows = getSqlMapClientTemplate().delete("V_AL_RB_LOSS_POWER.deleteByPrimaryKey", parms);
		return rows;
	}
	@SuppressWarnings("unchecked")
	@Override
	public VAlRbLossPower selectByPrimaryKey(int id) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID",id);
		parms.put("P_DATA", null);
		List<VAlRbLossPower> recordList = getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.selectByPrimaryKey", parms);
		VAlRbLossPower record = new VAlRbLossPower();
		if (recordList.size()>0)
		{
			record = recordList.get(0);
		}
        return record;
	}

	@Override
	public int UPDATEVAlRbLossPower(VAlRbLossPower record) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", record.getId());
		parms.put("P_BSCID", record.getBscid());
		parms.put("P_CELLID", record.getCellid());
		parms.put("P_SDATE", record.getSdate());
		parms.put("P_EDATE", record.getEdate());
		parms.put("P_DDH_BAO_MD", record.getDdhBaoMd());
		parms.put("P_MLL_SDATE", record.getMllSdate());
		parms.put("P_MLL_EDATE", record.getMllEdate());
		parms.put("P_DDH_BAO_MLL", record.getDdhBaoMll());
		parms.put("P_DVT_TEAM_PROCESS", record.getDvtTeamProcess());
		parms.put("P_DVT_USER_PROCESS", record.getDvtUserProcess());
		parms.put("P_UNG_CUU_MPD", record.getUngCuuMpd());
		parms.put("P_NODE_TRUYEN_DAN", record.getNodeTruyenDan());
		parms.put("P_AC_LOW", record.getAcLow());
		parms.put("P_MCH_1800", record.getMch1800());
		parms.put("P_DESCRIPTION", record.getDescription());
		parms.put("P_SHIFT_ID", record.getShiftId());
		parms.put("P_ALARM_ID", record.getAlarmId());
		parms.put("P_REGION", record.getRegion());
        int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_POWER.UPDATEVAlRbLossPower", parms);
        return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VAlRbLossPower> getVAlRbLossPowerFilter(String startTime,String endTime,
			String bscid, String cellid, int totalTimeFN, int totalTimeEN,
			String dvtTeamProcess, String dvtUserProcess, String ungCuuMpd,
			String nodeTruyenDan, String acLow, String mch1800,String statusKTMD,
			String statusKTMLL, int order, String column,String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_EDATE", endTime);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_timeTotalF", totalTimeFN);
		map.put("P_timeTotalE", totalTimeEN);
		map.put("P_DVT_TEAM_PROCESS", dvtTeamProcess);
		map.put("P_DVT_USER_PROCESS", dvtUserProcess);
		map.put("P_UNG_CUU_MPD", ungCuuMpd);
		map.put("P_NODE_TRUYEN_DAN", nodeTruyenDan);
		map.put("P_AC_LOW", acLow);
		map.put("P_MCH_1800", mch1800);
		map.put("P_statusKTMD", statusKTMD);
		map.put("P_statusKTMLL", statusKTMLL);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_REGION", region);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.getVAlRbLossPowerFilter", map);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_TYPE", typeForm);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.titleForm", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailLostConfig> getDetail(String bscId, String cellid,
			String timer, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", timer);
		map.put("P_BSCID", bscId);
		map.put("P_CELLID", cellid);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.getDetail", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VAlRbLossPower> checkExits(String startTime, String bscid,
			String cellid,String shiftSdateStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_SHIFT_SDATE", shiftSdateStr);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.checkExits", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VAlRbLossPower> getCompareLossPowerList(String startTime,
			String endTime, String cellid, String teamProcess, String siteName,
			String province, String district, String bscid, String strWhere,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder,String dept) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_EDATE", endTime);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_SITENAME", siteName);
		map.put("P_TEAM", teamProcess);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_WHERE", strWhere);
		map.put("P_START_RECORD", startRecord);
		map.put("P_END_RECORD", endRecord);
		map.put("P_COLUMN", sortfield);
		map.put("P_ORDER", sortorder);
		map.put("P_DEPT", dept);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.getCompareLossPowerList", map);
	}
/*
	@SuppressWarnings("unchecked")
	@Override
	public VAlRbLossPower selectCompareByID(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_ID", id);
		map.put("P_DATA", null);
		List<VAlRbLossPower> recordList = new ArrayList<VAlRbLossPower>();
		VAlRbLossPower record = new VAlRbLossPower();
		recordList = getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.selectCompareByID", map);
		if (recordList.size()>0)
		{
			record = recordList.get(0);
		}
		return record;
	}*/

	@Override
	public int updateCompareLossPower(VAlRbLossPower record, String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", record.getId());
		parms.put("P_ID_LOG", record.getIdLog());
		parms.put("P_BSCID", record.getBscid());
		parms.put("P_CELLID", record.getCellid());
		parms.put("P_SITENAME", record.getSiteName());
		parms.put("P_PROVINCE", record.getProvince());
		parms.put("P_DISTRICT", record.getDistrict());
		parms.put("P_DEPT", record.getDept());
		parms.put("P_TEAM", record.getTeam());
		parms.put("P_SUB_TEAM", record.getGroups());
		parms.put("P_SDATE", record.getSdate());
		parms.put("P_EDATE", record.getEdate());
		parms.put("P_SHIFT_SDATE", record.getShiftSdate());
		parms.put("P_SHIFT_EDATE", record.getShiftEdate());
		parms.put("P_SHIFT_ID", record.getShiftId());
		parms.put("P_SHIFT_USER", record.getShiftUser());
		parms.put("P_DESCRIPTION", record.getDescription());
		parms.put("P_TYPEFORM", typeForm);
		parms.put("P_SDATE_STR", record.getSdateStr());
		parms.put("P_SHIFT_SDATE_STR", record.getShiftSdateStr());
		parms.put("P_ASSESS", record.getAssess());
		
        int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_POWER.updateCompareLossPower", parms);
        return rows;
	}

	@Override
	public int countRAlarmLoss(String startTime, String endTime, String cellid,
			String teamProcess, String siteName, String province,
			String district, String bscid, String strWhere,String dept) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_EDATE", endTime);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_SITENAME", siteName);
		map.put("P_TEAM", teamProcess);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_WHERE", strWhere);
		map.put("P_DEPT", dept);
		
		map.put("P_DATA", null);
		getSqlMapClientTemplate().queryForObject("V_AL_RB_LOSS_POWER.countRAlarmLoss", map);
      	Integer record = (Integer) map.get("P_DATA");
      	return record;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public VAlRbLossPower selectCompareByID(Integer id, String idLog,String bscid,
			String cellid, String sdate, String shiftEdate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_ID", id);
		map.put("P_ID_LOG", idLog);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_SDATE", sdate);
		map.put("P_SHIFT_SDATE", shiftEdate);
		map.put("P_DATA", null);
		List<VAlRbLossPower> recordList = new ArrayList<VAlRbLossPower>();
		VAlRbLossPower record = new VAlRbLossPower();
		recordList = getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.selectCompareByID", map);
		if (recordList.size()>0)
		{
			record = recordList.get(0);
		}
		return record;
	}

	@Override
	public int insertRAlarmLossPowerTemp(VAlRbLossPower recordLossPW) {
		Map<String, Object> parms = new HashMap<String, Object>();
		/*parms.put("P_ID", recordLossPW.getId());
		parms.put("P_ID_LOG", recordLossPW.getIdLog());*/
		
		String bsc = recordLossPW.getBscid();
		String cell = recordLossPW.getCellid();
		String site = recordLossPW.getSiteName();
		String province = recordLossPW.getProvince();
		String district = recordLossPW.getDistrict();
		Date sdate = recordLossPW.getShiftSdate();
		Date edate = recordLossPW.getShiftEdate();
		
		parms.put("P_BSCID", recordLossPW.getBscid());
		parms.put("P_CELLID", recordLossPW.getCellid());
		parms.put("P_SITENAME", recordLossPW.getSiteName());
		parms.put("P_PROVINCE", recordLossPW.getProvince());
		parms.put("P_DISTRICT", recordLossPW.getDistrict());
		parms.put("P_DEPT", recordLossPW.getDept());
		parms.put("P_TEAM", recordLossPW.getTeam());
		parms.put("P_SUB_TEAM", recordLossPW.getGroups());
		parms.put("P_SDATE", recordLossPW.getSdate());
		parms.put("P_EDATE", recordLossPW.getEdate());
		parms.put("P_SHIFT_SDATE", recordLossPW.getShiftSdate());
		parms.put("P_SHIFT_EDATE", recordLossPW.getShiftEdate());
		parms.put("P_SHIFT_ID", recordLossPW.getShiftId());
		parms.put("P_SHIFT_USER", recordLossPW.getShiftUser());
		parms.put("P_DESCRIPTION", recordLossPW.getDescription());
		parms.put("P_ASSESS", recordLossPW.getAssess());
        int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_POWER.insertRAlarmLossPowerTemp", parms);
        return rows;
	}

	@Override
	public int deleteAlarmLossPowerCompare(String idList, String idLogList) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID_LIST", idList);
		parms.put("P_ID_LOG_LIST", idLogList);
        int rows = getSqlMapClientTemplate().delete("V_AL_RB_LOSS_POWER.deleteAlarmLossPowerCompare", parms);
        return rows;
	}

	/*Lay danh sach canh bao mat dien khop va khong khop. AnhCTV 16/10/2015*/
	@SuppressWarnings("unchecked")
	@Override
	public List<VAlRbLossPower> getCompareLossPowerMLMNList(String startTime,
			String endTime, String cellid, String teamProcess, String siteName,
			String province, String district, String bscid, String dept,
			String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_EDATE", endTime);
		map.put("P_BSCID", bscid);
		map.put("P_CELLID", cellid);
		map.put("P_SITENAME", siteName);
		map.put("P_TEAM", teamProcess);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_DEPT", dept);
		map.put("P_TYPE", type);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_POWER.getCompareLossPowerMLMNList", map);
	}

	
}