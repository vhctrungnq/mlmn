package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpHrBscCssr;

public class VRpHrBscCssrDAOImpl extends SqlMapClientDaoSupport implements VRpHrBscCssrDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR
     *
     * @ibatorgenerated Tue Apr 16 09:32:23 ICT 2013
     */
    public VRpHrBscCssrDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR
     *
     * @ibatorgenerated Tue Apr 16 09:32:23 ICT 2013
     */
    public void insert(VRpHrBscCssr record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_CSSR.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR
     *
     * @ibatorgenerated Tue Apr 16 09:32:23 ICT 2013
     */
    public void insertSelective(VRpHrBscCssr record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_CSSR.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpHrBscCssr> getListCssr(String bscid) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_BSCID", bscid);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_CSSR.getListCssr", parms);
    }
    @SuppressWarnings("unchecked")
    public List<VRpHrBscCssr> filter(String bscid) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_BSCID", bscid);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_CSSR.getListCssrByBscid", parms);
    }
}