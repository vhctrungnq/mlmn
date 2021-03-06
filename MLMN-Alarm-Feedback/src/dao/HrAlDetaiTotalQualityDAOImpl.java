package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.HrAlDetaiTotalQuality;
import vo.SYS_PARAMETER;

public class HrAlDetaiTotalQualityDAOImpl extends SqlMapClientDaoSupport implements HrAlDetaiTotalQualityDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public HrAlDetaiTotalQualityDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public int deleteByPrimaryKey(Integer id) {
        HrAlDetaiTotalQuality key = new HrAlDetaiTotalQuality();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public void insert(HrAlDetaiTotalQuality record) {
        getSqlMapClientTemplate().insert("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public void insertSelective(HrAlDetaiTotalQuality record) {
        getSqlMapClientTemplate().insert("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public HrAlDetaiTotalQuality selectByPrimaryKey(Integer id) {
        HrAlDetaiTotalQuality key = new HrAlDetaiTotalQuality();
        key.setId(id);
        HrAlDetaiTotalQuality record = (HrAlDetaiTotalQuality) getSqlMapClientTemplate().queryForObject("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public int updateByPrimaryKeySelective(HrAlDetaiTotalQuality record) {
        int rows = getSqlMapClientTemplate().update("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_DETAIL_TOTAL_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:52:55 ICT 2015
     */
    public int updateByPrimaryKey(HrAlDetaiTotalQuality record) {
        int rows = getSqlMapClientTemplate().update("HR_AL_DETAIL_TOTAL_QUALITY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<HrAlDetaiTotalQuality> getReportHrWithSeverity(String sdate,
			String edate, String shour, String ehour, String network,
			String province, String vendor, String team, String district,
			String bscid, String region, String type, String function,
			String username) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_FROM_DAY", sdate);
   		parms.put("P_TO_DAY", edate);
   		parms.put("P_FROM_HOUR", shour);
   		parms.put("P_TO_HOUR", ehour);
   		parms.put("P_NETWORK", network);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_TEAM", team);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_NE", bscid);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_USERNAME", username);
   		parms.put("P_REGION", region);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("HR_AL_DETAIL_TOTAL_QUALITY.getReportHrWithSeverity", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeSum, String function,
			String type, String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE_SUM", typeSum);
   		parms.put("P_TYPE_FUNTION", function);
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_TYPE_FORM", typeForm);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("HR_AL_DETAIL_TOTAL_QUALITY.titleForm", parms);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleFormChartHr(String function, String type,
			String seriestype) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_TYPE_SUM", seriestype);
   		parms.put("P_TYPE_FUNTION", function);
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("HR_AL_DETAIL_TOTAL_QUALITY.titleFormChartHr", parms);
	}
	/*lay du lieu ve bieu do so luong muc gio. AnhCTV:29/10/2015
	 * P_TYPE_FUNTION: finish, nonfinish, total..
	 * P_TYPE_GROUP: NE, severity, team, province, region ,all
	 * P_ALARM_TYPE: MAT LIEN LAC, MAT DIEN, 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<HrAlDetaiTotalQuality> getReportHrChart(String function, String type,
			String alarmtype, Date startDate, Date endDate, String startHour,
			String endHour, String network, String province, String vendor,
			String team, String district, String bscid, String region,
			String username){
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_FROM_DAY", startDate);
   		parms.put("P_TO_DAY", endDate);
   		parms.put("P_FROM_HOUR", startHour);
   		parms.put("P_TO_HOUR", endHour);
   		parms.put("P_NETWORK", network);
   		parms.put("P_PROVINCE", province);
   		parms.put("P_VENDOR", vendor);
   		parms.put("P_TEAM", team);
   		parms.put("P_DISTRICT", district);
   		parms.put("P_NE", bscid);
   		parms.put("P_REGION", region);
   		parms.put("P_USERNAME", username);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_TYPE_GROUP", type);
   		parms.put("P_ALARM_TYPE", alarmtype);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("HR_AL_DETAIL_TOTAL_QUALITY.getReportHrChart", parms);
	}
/*	lay du lieu cho bieu do muc gio -severity . AnhCTV.29/10/2015*/
	@SuppressWarnings("unchecked")
	@Override
	public List<HrAlDetaiTotalQuality> getReportHrChartSeverity(
			String function, String alarmtype, Date sdate, Date edate,
			String startHour, String endHour, String column, String order) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_FROM_DAY", sdate);
   		parms.put("P_TO_DAY", edate);
   		parms.put("P_FROM_HOUR", startHour);
   		parms.put("P_TO_HOUR", endHour);
   		parms.put("P_TYPE_FUNTION",function );
   		parms.put("P_ALARM_TYPE", alarmtype);
   		parms.put("P_COLUMN", column);
   		parms.put("P_ORDER", order);
   		parms.put("P_DATA", null);
   		return getSqlMapClientTemplate().queryForList("HR_AL_DETAIL_TOTAL_QUALITY.getReportHrChartSeverity", parms);
	}
}