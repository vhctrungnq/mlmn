package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkStpChecksumQos;

public class WkStpChecksumQosDAOImpl extends SqlMapClientDaoSupport implements WkStpChecksumQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpChecksumQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int deleteByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpChecksumQos key = new WkStpChecksumQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_STP_CHECKSUM_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insert(WkStpChecksumQos record) {
        getSqlMapClientTemplate().insert("WK_STP_CHECKSUM_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insertSelective(WkStpChecksumQos record) {
        getSqlMapClientTemplate().insert("WK_STP_CHECKSUM_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpChecksumQos selectByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpChecksumQos key = new WkStpChecksumQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        WkStpChecksumQos record = (WkStpChecksumQos) getSqlMapClientTemplate().queryForObject("WK_STP_CHECKSUM_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkStpChecksumQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_CHECKSUM_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_CHECKSUM_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKey(WkStpChecksumQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_CHECKSUM_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}