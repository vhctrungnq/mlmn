package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnPASR3g;

public class VRpMnPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpMnPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:18 ICT 2016
     */
    public VRpMnPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:18 ICT 2016
     */
    public void insert(VRpMnPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:18 ICT 2016
     */
    public void insertSelective(VRpMnPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PASR_3G.ibatorgenerated_insertSelective", record);
    }
}