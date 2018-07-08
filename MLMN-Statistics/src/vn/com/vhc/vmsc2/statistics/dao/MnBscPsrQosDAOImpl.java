package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnBscPsrQos;

public class MnBscPsrQosDAOImpl extends SqlMapClientDaoSupport implements MnBscPsrQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscPsrQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscPsrQos key = new MnBscPsrQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_BSC_PSR_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insert(MnBscPsrQos record) {
        getSqlMapClientTemplate().insert("MN_BSC_PSR_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insertSelective(MnBscPsrQos record) {
        getSqlMapClientTemplate().insert("MN_BSC_PSR_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscPsrQos selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscPsrQos key = new MnBscPsrQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnBscPsrQos record = (MnBscPsrQos) getSqlMapClientTemplate().queryForObject("MN_BSC_PSR_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnBscPsrQos record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_PSR_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKey(MnBscPsrQos record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_PSR_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}