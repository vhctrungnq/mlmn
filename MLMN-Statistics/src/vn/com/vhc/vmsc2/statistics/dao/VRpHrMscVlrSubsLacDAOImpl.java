package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrMscVlrSubsLac;
import vn.com.vhc.vmsc2.statistics.web.filter.MscVlrSubsFilter;

public class VRpHrMscVlrSubsLacDAOImpl extends SqlMapClientDaoSupport implements VRpHrMscVlrSubsLacDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_MSC_VLRSUBS_LAC
     *
     * @ibatorgenerated Fri Jan 10 14:31:42 ICT 2014
     */
    public VRpHrMscVlrSubsLacDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_MSC_VLRSUBS_LAC
     *
     * @ibatorgenerated Fri Jan 10 14:31:42 ICT 2014
     */
    public void insert(VRpHrMscVlrSubsLac record) {
        getSqlMapClientTemplate().insert("V_RP_HR_MSC_VLRSUBS_LAC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_MSC_VLRSUBS_LAC
     *
     * @ibatorgenerated Fri Jan 10 14:31:42 ICT 2014
     */
    public void insertSelective(VRpHrMscVlrSubsLac record) {
        getSqlMapClientTemplate().insert("V_RP_HR_MSC_VLRSUBS_LAC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpHrMscVlrSubsLac> getDataList(MscVlrSubsFilter filter) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("p_sdate", filter.getSdate());
    	map.put("p_edate", filter.getEdate());
    	map.put("p_shour", filter.getShour());
    	map.put("p_ehour", filter.getEhour());
    	map.put("p_mscid", filter.getMscid());
    	map.put("p_cn", filter.getCn());
    	map.put("p_bscid", filter.getBscid());
		map.put("P_DATA", null); 
		
		return getSqlMapClientTemplate().queryForList("V_RP_HR_MSC_VLRSUBS_LAC.getHrRpMscVlrLac", map);
    }
}