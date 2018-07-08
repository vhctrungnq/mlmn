package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnHlrSubscribersQos;

public class MnHlrSubscribersQosDAOImpl extends SqlMapClientDaoSupport implements MnHlrSubscribersQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public MnHlrSubscribersQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public int deleteByPrimaryKey(String hlrid, Integer month, Integer year) {
        MnHlrSubscribersQos key = new MnHlrSubscribersQos();
        key.setHlrid(hlrid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public void insert(MnHlrSubscribersQos record) {
        getSqlMapClientTemplate().insert("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public void insertSelective(MnHlrSubscribersQos record) {
        getSqlMapClientTemplate().insert("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public MnHlrSubscribersQos selectByPrimaryKey(String hlrid, Integer month, Integer year) {
        MnHlrSubscribersQos key = new MnHlrSubscribersQos();
        key.setHlrid(hlrid);
        key.setMonth(month);
        key.setYear(year);
        MnHlrSubscribersQos record = (MnHlrSubscribersQos) getSqlMapClientTemplate().queryForObject("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnHlrSubscribersQos record) {
        int rows = getSqlMapClientTemplate().update("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    public int updateByPrimaryKey(MnHlrSubscribersQos record) {
        int rows = getSqlMapClientTemplate().update("MN_HLR_SUBSCRIBERS_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}