package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrPASR3g;

public class VRpYrPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpYrPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:39 ICT 2016
     */
    public VRpYrPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:39 ICT 2016
     */
    public void insert(VRpYrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:39 ICT 2016
     */
    public void insertSelective(VRpYrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_PASR_3G.ibatorgenerated_insertSelective", record);
    }
}