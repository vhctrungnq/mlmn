package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnStpDestinationQos;

public class MnStpDestinationQosDAOImpl extends SqlMapClientDaoSupport implements MnStpDestinationQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public MnStpDestinationQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int deleteByPrimaryKey(Integer month, String stpid, Integer year) {
        MnStpDestinationQos key = new MnStpDestinationQos();
        key.setMonth(month);
        key.setStpid(stpid);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_STP_DESTINATION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public void insert(MnStpDestinationQos record) {
        getSqlMapClientTemplate().insert("MN_STP_DESTINATION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public void insertSelective(MnStpDestinationQos record) {
        getSqlMapClientTemplate().insert("MN_STP_DESTINATION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public MnStpDestinationQos selectByPrimaryKey(Integer month, String stpid, Integer year) {
        MnStpDestinationQos key = new MnStpDestinationQos();
        key.setMonth(month);
        key.setStpid(stpid);
        key.setYear(year);
        MnStpDestinationQos record = (MnStpDestinationQos) getSqlMapClientTemplate().queryForObject("MN_STP_DESTINATION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnStpDestinationQos record) {
        int rows = getSqlMapClientTemplate().update("MN_STP_DESTINATION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int updateByPrimaryKey(MnStpDestinationQos record) {
        int rows = getSqlMapClientTemplate().update("MN_STP_DESTINATION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}