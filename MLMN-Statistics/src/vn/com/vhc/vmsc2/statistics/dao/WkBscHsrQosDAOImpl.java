package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkBscHsrQos;

public class WkBscHsrQosDAOImpl extends SqlMapClientDaoSupport implements WkBscHsrQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public WkBscHsrQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer week, Integer year) {
        WkBscHsrQos key = new WkBscHsrQos();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_BSC_HSR_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public void insert(WkBscHsrQos record) {
        getSqlMapClientTemplate().insert("WK_BSC_HSR_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public void insertSelective(WkBscHsrQos record) {
        getSqlMapClientTemplate().insert("WK_BSC_HSR_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public WkBscHsrQos selectByPrimaryKey(String bscid, Integer week, Integer year) {
        WkBscHsrQos key = new WkBscHsrQos();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        WkBscHsrQos record = (WkBscHsrQos) getSqlMapClientTemplate().queryForObject("WK_BSC_HSR_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkBscHsrQos record) {
        int rows = getSqlMapClientTemplate().update("WK_BSC_HSR_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    public int updateByPrimaryKey(WkBscHsrQos record) {
        int rows = getSqlMapClientTemplate().update("WK_BSC_HSR_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}