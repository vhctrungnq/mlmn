package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrHlrMapQos;

public class HrHlrMapQosDAOImpl extends SqlMapClientDaoSupport implements HrHlrMapQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public HrHlrMapQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String hlrid, Integer hour) {
        HrHlrMapQos key = new HrHlrMapQos();
        key.setDay(day);
        key.setHlrid(hlrid);
        key.setHour(hour);
        int rows = getSqlMapClientTemplate().delete("HR_HLR_MAP_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public void insert(HrHlrMapQos record) {
        getSqlMapClientTemplate().insert("HR_HLR_MAP_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public void insertSelective(HrHlrMapQos record) {
        getSqlMapClientTemplate().insert("HR_HLR_MAP_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public HrHlrMapQos selectByPrimaryKey(Date day, String hlrid, Integer hour) {
        HrHlrMapQos key = new HrHlrMapQos();
        key.setDay(day);
        key.setHlrid(hlrid);
        key.setHour(hour);
        HrHlrMapQos record = (HrHlrMapQos) getSqlMapClientTemplate().queryForObject("HR_HLR_MAP_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrHlrMapQos record) {
        int rows = getSqlMapClientTemplate().update("HR_HLR_MAP_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:10 ICT 2010
     */
    public int updateByPrimaryKey(HrHlrMapQos record) {
        int rows = getSqlMapClientTemplate().update("HR_HLR_MAP_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}