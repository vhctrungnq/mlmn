package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkPASR3g;

public class VRpWkPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpWkPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:10 ICT 2016
     */
    public VRpWkPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:10 ICT 2016
     */
    public void insert(VRpWkPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:10 ICT 2016
     */
    public void insertSelective(VRpWkPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PASR_3G.ibatorgenerated_insertSelective", record);
    }
}