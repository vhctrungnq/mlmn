package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyHBscCpuQos;

public class DyHBscCpuQosDAOImpl extends SqlMapClientDaoSupport implements DyHBscCpuQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public DyHBscCpuQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Date day) {
        DyHBscCpuQos key = new DyHBscCpuQos();
        key.setBscid(bscid);
        key.setDay(day);
        int rows = getSqlMapClientTemplate().delete("DY_H_BSC_CPU_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public void insert(DyHBscCpuQos record) {
        getSqlMapClientTemplate().insert("DY_H_BSC_CPU_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public void insertSelective(DyHBscCpuQos record) {
        getSqlMapClientTemplate().insert("DY_H_BSC_CPU_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public DyHBscCpuQos selectByPrimaryKey(String bscid, Date day) {
        DyHBscCpuQos key = new DyHBscCpuQos();
        key.setBscid(bscid);
        key.setDay(day);
        DyHBscCpuQos record = (DyHBscCpuQos) getSqlMapClientTemplate().queryForObject("DY_H_BSC_CPU_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyHBscCpuQos record) {
        int rows = getSqlMapClientTemplate().update("DY_H_BSC_CPU_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    public int updateByPrimaryKey(DyHBscCpuQos record) {
        int rows = getSqlMapClientTemplate().update("DY_H_BSC_CPU_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}