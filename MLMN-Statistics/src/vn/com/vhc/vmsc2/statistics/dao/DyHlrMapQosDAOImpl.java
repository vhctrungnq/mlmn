package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyHlrMapQos;

public class DyHlrMapQosDAOImpl extends SqlMapClientDaoSupport implements DyHlrMapQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public DyHlrMapQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String hlrid) {
        DyHlrMapQos key = new DyHlrMapQos();
        key.setDay(day);
        key.setHlrid(hlrid);
        int rows = getSqlMapClientTemplate().delete("DY_HLR_MAP_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public void insert(DyHlrMapQos record) {
        getSqlMapClientTemplate().insert("DY_HLR_MAP_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public void insertSelective(DyHlrMapQos record) {
        getSqlMapClientTemplate().insert("DY_HLR_MAP_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public DyHlrMapQos selectByPrimaryKey(Date day, String hlrid) {
        DyHlrMapQos key = new DyHlrMapQos();
        key.setDay(day);
        key.setHlrid(hlrid);
        DyHlrMapQos record = (DyHlrMapQos) getSqlMapClientTemplate().queryForObject("DY_HLR_MAP_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyHlrMapQos record) {
        int rows = getSqlMapClientTemplate().update("DY_HLR_MAP_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    public int updateByPrimaryKey(DyHlrMapQos record) {
        int rows = getSqlMapClientTemplate().update("DY_HLR_MAP_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}