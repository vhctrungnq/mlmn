package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDyAccNwCoreCs;

public class VRpDyAccNwCoreCsDAOImpl extends SqlMapClientDaoSupport implements VRpDyAccNwCoreCsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_CORE_CS
     *
     * @ibatorgenerated Thu Nov 01 11:07:38 ICT 2012
     */
    public VRpDyAccNwCoreCsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_CORE_CS
     *
     * @ibatorgenerated Thu Nov 01 11:07:38 ICT 2012
     */
    public void insert(VRpDyAccNwCoreCs record) {
        getSqlMapClientTemplate().insert("V_RP_DY_ACC_NW_CORE_CS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_CORE_CS
     *
     * @ibatorgenerated Thu Nov 01 11:07:38 ICT 2012
     */
    public void insertSelective(VRpDyAccNwCoreCs record) {
        getSqlMapClientTemplate().insert("V_RP_DY_ACC_NW_CORE_CS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyAccNwCoreCs> filter(String day){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("day", day);
    return getSqlMapClientTemplate().queryForList("V_RP_DY_ACC_NW_CORE_CS.filter", map);
    }
}