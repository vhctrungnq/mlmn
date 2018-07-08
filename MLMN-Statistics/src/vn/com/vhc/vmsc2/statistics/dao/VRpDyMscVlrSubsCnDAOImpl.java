package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscVlrSubsCn;
import vn.com.vhc.vmsc2.statistics.web.filter.MscVlrSubsFilter;

public class VRpDyMscVlrSubsCnDAOImpl extends SqlMapClientDaoSupport implements VRpDyMscVlrSubsCnDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_VLRSUBS_CN
     *
     * @ibatorgenerated Fri Jan 10 14:31:12 ICT 2014
     */
    public VRpDyMscVlrSubsCnDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_VLRSUBS_CN
     *
     * @ibatorgenerated Fri Jan 10 14:31:12 ICT 2014
     */
    public void insert(VRpDyMscVlrSubsCn record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_VLRSUBS_CN.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_VLRSUBS_CN
     *
     * @ibatorgenerated Fri Jan 10 14:31:12 ICT 2014
     */
    public void insertSelective(VRpDyMscVlrSubsCn record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_VLRSUBS_CN.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyMscVlrSubsCn> getDataList(MscVlrSubsFilter filter) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("p_sdate", filter.getSdate());
    	map.put("p_edate", filter.getEdate());
    	map.put("p_mscid", filter.getMscid());
		map.put("P_DATA", null); 
		
		return getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_VLRSUBS_CN.getRpMscVlrCn", map);
    }
}