package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyPASR3g;

public class VRpDyPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpDyPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:02 ICT 2016
     */
    public VRpDyPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:02 ICT 2016
     */
    public void insert(VRpDyPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:02 ICT 2016
     */
    public void insertSelective(VRpDyPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_PASR_3G.ibatorgenerated_insertSelective", record);
    }
}