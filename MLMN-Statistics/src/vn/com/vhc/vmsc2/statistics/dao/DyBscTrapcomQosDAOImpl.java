package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyBscTrapcomQos;

public class DyBscTrapcomQosDAOImpl extends SqlMapClientDaoSupport implements DyBscTrapcomQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public DyBscTrapcomQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Date day) {
        DyBscTrapcomQos key = new DyBscTrapcomQos();
        key.setBscid(bscid);
        key.setDay(day);
        int rows = getSqlMapClientTemplate().delete("DY_BSC_TRAPCOM_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public void insert(DyBscTrapcomQos record) {
        getSqlMapClientTemplate().insert("DY_BSC_TRAPCOM_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public void insertSelective(DyBscTrapcomQos record) {
        getSqlMapClientTemplate().insert("DY_BSC_TRAPCOM_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public DyBscTrapcomQos selectByPrimaryKey(String bscid, Date day) {
        DyBscTrapcomQos key = new DyBscTrapcomQos();
        key.setBscid(bscid);
        key.setDay(day);
        DyBscTrapcomQos record = (DyBscTrapcomQos) getSqlMapClientTemplate().queryForObject("DY_BSC_TRAPCOM_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyBscTrapcomQos record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_TRAPCOM_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_TRAPCOM_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int updateByPrimaryKey(DyBscTrapcomQos record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_TRAPCOM_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}