package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscPchQos;

public class MnHBscPchQosDAOImpl extends SqlMapClientDaoSupport implements MnHBscPchQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscPchQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscPchQos key = new MnHBscPchQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_H_BSC_PCH_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insert(MnHBscPchQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_PCH_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insertSelective(MnHBscPchQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_PCH_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscPchQos selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscPchQos key = new MnHBscPchQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnHBscPchQos record = (MnHBscPchQos) getSqlMapClientTemplate().queryForObject("MN_H_BSC_PCH_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnHBscPchQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_PCH_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKey(MnHBscPchQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_PCH_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}