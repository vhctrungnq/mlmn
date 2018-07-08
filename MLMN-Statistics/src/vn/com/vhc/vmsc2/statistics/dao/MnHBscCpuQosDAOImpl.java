package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscCpuQos;

public class MnHBscCpuQosDAOImpl extends SqlMapClientDaoSupport implements MnHBscCpuQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscCpuQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscCpuQos key = new MnHBscCpuQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_H_BSC_CPU_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insert(MnHBscCpuQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_CPU_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insertSelective(MnHBscCpuQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_CPU_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscCpuQos selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscCpuQos key = new MnHBscCpuQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnHBscCpuQos record = (MnHBscCpuQos) getSqlMapClientTemplate().queryForObject("MN_H_BSC_CPU_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnHBscCpuQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_CPU_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKey(MnHBscCpuQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_CPU_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}