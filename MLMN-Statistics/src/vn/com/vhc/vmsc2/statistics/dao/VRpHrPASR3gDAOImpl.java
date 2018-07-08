package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrPASR3g;

public class VRpHrPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpHrPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 08:59:56 ICT 2016
     */
    public VRpHrPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 08:59:56 ICT 2016
     */
    public void insert(VRpHrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 08:59:56 ICT 2016
     */
    public void insertSelective(VRpHrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PASR_3G.ibatorgenerated_insertSelective", record);
    }

	@Override
	public List<String> getAllBsc() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("V_RP_HR_PASR_3G.getAllBsc");
	}

	@Override
	public List<String> getAllCellByBsc(String bscid) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("V_RP_HR_PASR_3G.getAllCellByBsc");
	}

	@Override
	public List<VRpHrPASR3g> getHrData(String bscid, String cellid, Integer startHour, String startDate, Integer endHour, String endDate) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("p_bsc", bscid);
		params.put("p_cell", cellid);
		params.put("p_start_hour", startHour);
		params.put("p_start_date", startDate);
		params.put("p_end_hour", endHour);
		params.put("p_end_date", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_PASR_3G.getHrData", params);
	}
}