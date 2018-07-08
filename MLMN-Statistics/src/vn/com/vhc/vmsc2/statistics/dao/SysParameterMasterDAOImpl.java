package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.SysParameterMaster;

public class SysParameterMasterDAOImpl extends SqlMapClientDaoSupport implements SysParameterMasterDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER_MASTER
     *
     * @ibatorgenerated Wed Sep 26 17:45:28 ICT 2012
     */
    public SysParameterMasterDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER_MASTER
     *
     * @ibatorgenerated Wed Sep 26 17:45:28 ICT 2012
     */
    public void insert(SysParameterMaster record) {
        getSqlMapClientTemplate().insert("SYS_PARAMETER_MASTER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER_MASTER
     *
     * @ibatorgenerated Wed Sep 26 17:45:28 ICT 2012
     */
    public void insertSelective(SysParameterMaster record) {
        getSqlMapClientTemplate().insert("SYS_PARAMETER_MASTER.ibatorgenerated_insertSelective", record);
    }
}