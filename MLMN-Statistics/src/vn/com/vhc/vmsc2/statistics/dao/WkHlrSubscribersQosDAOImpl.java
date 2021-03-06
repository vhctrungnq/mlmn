package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkHlrSubscribersQos;

public class WkHlrSubscribersQosDAOImpl extends SqlMapClientDaoSupport implements WkHlrSubscribersQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public WkHlrSubscribersQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public int deleteByPrimaryKey(String hlrid, Integer week, Integer year) {
        WkHlrSubscribersQos key = new WkHlrSubscribersQos();
        key.setHlrid(hlrid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public void insert(WkHlrSubscribersQos record) {
        getSqlMapClientTemplate().insert("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public void insertSelective(WkHlrSubscribersQos record) {
        getSqlMapClientTemplate().insert("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public WkHlrSubscribersQos selectByPrimaryKey(String hlrid, Integer week, Integer year) {
        WkHlrSubscribersQos key = new WkHlrSubscribersQos();
        key.setHlrid(hlrid);
        key.setWeek(week);
        key.setYear(year);
        WkHlrSubscribersQos record = (WkHlrSubscribersQos) getSqlMapClientTemplate().queryForObject("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkHlrSubscribersQos record) {
        int rows = getSqlMapClientTemplate().update("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:02:06 ICT 2010
     */
    public int updateByPrimaryKey(WkHlrSubscribersQos record) {
        int rows = getSqlMapClientTemplate().update("WK_HLR_SUBSCRIBERS_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}