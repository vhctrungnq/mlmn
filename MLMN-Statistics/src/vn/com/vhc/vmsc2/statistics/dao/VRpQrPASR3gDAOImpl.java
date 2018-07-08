package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrPASR3g;

public class VRpQrPASR3gDAOImpl extends SqlMapClientDaoSupport implements VRpQrPASR3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:31 ICT 2016
     */
    public VRpQrPASR3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:31 ICT 2016
     */
    public void insert(VRpQrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_QR_PASR_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_PASR_3G
     *
     * @ibatorgenerated Thu Dec 15 09:00:31 ICT 2016
     */
    public void insertSelective(VRpQrPASR3g record) {
        getSqlMapClientTemplate().insert("V_RP_QR_PASR_3G.ibatorgenerated_insertSelective", record);
    }
}