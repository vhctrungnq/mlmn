package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscCssrDetails3G;

public class VRpHrBscCssrDetails3GDAOImpl extends SqlMapClientDaoSupport implements VRpHrBscCssrDetails3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public VRpHrBscCssrDetails3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insert(VRpHrBscCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_CSSR_DETAILS_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insertSelective(VRpHrBscCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_CSSR_DETAILS_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpHrBscCssrDetails3G> filter(String startHour, String endHour, Date day, String bscid, String region) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_CSSR_DETAILS_3G.filter", map);
	}
}