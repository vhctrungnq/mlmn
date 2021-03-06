package dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.DyAlDetailFinish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DyAlDetailFinishDAOImpl extends SqlMapClientDaoSupport implements DyAlDetailFinishDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public DyAlDetailFinishDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        DyAlDetailFinish key = new DyAlDetailFinish();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("DY_AL_DETAIL_FINISH.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public void insert(DyAlDetailFinish record) {
        getSqlMapClientTemplate().insert("DY_AL_DETAIL_FINISH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public void insertSelective(DyAlDetailFinish record) {
        getSqlMapClientTemplate().insert("DY_AL_DETAIL_FINISH.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public DyAlDetailFinish selectByPrimaryKey(Integer id) {
        DyAlDetailFinish key = new DyAlDetailFinish();
        key.setId(id);
        DyAlDetailFinish record = (DyAlDetailFinish) getSqlMapClientTemplate().queryForObject("DY_AL_DETAIL_FINISH.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public int updateByPrimaryKeySelective(DyAlDetailFinish record) {
        int rows = getSqlMapClientTemplate().update("DY_AL_DETAIL_FINISH.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH
     *
     * @ibatorgenerated Thu Oct 24 16:49:29 ICT 2013
     */
    public int updateByPrimaryKey(DyAlDetailFinish record) {
        int rows = getSqlMapClientTemplate().update("DY_AL_DETAIL_FINISH.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<DyAlDetailFinish> getReportDataTransmission(String sdate,
			String edate, String network, String province, String vendor,
			String bscid, String site, String team, String district,
			String alarmMappingName, String severity,Integer quality,  String function,
			String username, String alarmType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FROM_DAY", sdate);
   		parms.put("P_TO_DAY", edate);
   		parms.put("P_NETWORK", network);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_TEAM", team);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_NE", bscid);
   		parms.put("P_SITE",site);
   		parms.put("P_SEVERITY", severity);
   		parms.put("P_ALARM_MAPPING_NAME", alarmMappingName);
   		parms.put("P_USERNAME", username);
   		parms.put("P_QUALITY_LIMIT", quality);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_ALARM_TYPE",alarmType );
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("DY_AL_DETAIL_FINISH.getReportDataTransmission", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DyAlDetailFinish> getReportTransmissionAbis(String sdate,
			String edate, String network, String province, String vendor,
			String team, String district, String bscid, String type,Integer quality, 
			String function, String username) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FROM_DAY", sdate);
   		parms.put("P_TO_DAY", edate);
   		parms.put("P_NETWORK", network);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_TEAM", team);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_NE", bscid);
   		parms.put("P_USERNAME", username);
   		parms.put("P_QUALITY_LIMIT", quality);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("DY_AL_DETAIL_FINISH.getReportTransmissionAbis", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getReporTransmissionChart(String type,
			String sdateChar, String edateChar, String network,
			String province, String vendor, String team, String district,
			String bscid, String username, Integer quality, String function) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FROM_DAY", sdateChar);
   		parms.put("P_TO_DAY", edateChar);
   		parms.put("P_NETWORK", network);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_TEAM", team);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_NE", bscid);
   		parms.put("P_USERNAME", username);
   		parms.put("P_QUALITY_LIMIT", quality);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("DY_AL_DETAIL_FINISH.getReporTransmissionChart", parms);
	}
}