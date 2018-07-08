package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnBscDcrtQos;

public class MnBscDcrtQosDAOImpl extends SqlMapClientDaoSupport implements MnBscDcrtQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscDcrtQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscDcrtQos key = new MnBscDcrtQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_BSC_DCRT_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insert(MnBscDcrtQos record) {
        getSqlMapClientTemplate().insert("MN_BSC_DCRT_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insertSelective(MnBscDcrtQos record) {
        getSqlMapClientTemplate().insert("MN_BSC_DCRT_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscDcrtQos selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscDcrtQos key = new MnBscDcrtQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnBscDcrtQos record = (MnBscDcrtQos) getSqlMapClientTemplate().queryForObject("MN_BSC_DCRT_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnBscDcrtQos record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_DCRT_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKey(MnBscDcrtQos record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_DCRT_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}