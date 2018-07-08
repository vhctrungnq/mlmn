package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRDyBranchGprs;

public class VRDyBranchGprsDAOImpl extends SqlMapClientDaoSupport implements VRDyBranchGprsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_DY_BRANCH_GPRSEDGE
     *
     * @ibatorgenerated Tue Aug 14 11:43:53 ICT 2012
     */
    public VRDyBranchGprsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_DY_BRANCH_GPRSEDGE
     *
     * @ibatorgenerated Tue Aug 14 11:43:53 ICT 2012
     */
    public void insert(VRDyBranchGprs record) {
        getSqlMapClientTemplate().insert("V_R_DY_BRANCH_GPRSEDGE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_DY_BRANCH_GPRSEDGE
     *
     * @ibatorgenerated Tue Aug 14 11:43:53 ICT 2012
     */
    public void insertSelective(VRDyBranchGprs record) {
        getSqlMapClientTemplate().insert("V_R_DY_BRANCH_GPRSEDGE.ibatorgenerated_insertSelective", record);
    }
}