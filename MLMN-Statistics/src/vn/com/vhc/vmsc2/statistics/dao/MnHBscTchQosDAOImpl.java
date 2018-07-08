package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscTchQos;

public class MnHBscTchQosDAOImpl extends SqlMapClientDaoSupport implements MnHBscTchQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscTchQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscTchQos key = new MnHBscTchQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_H_BSC_TCH_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insert(MnHBscTchQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_TCH_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public void insertSelective(MnHBscTchQos record) {
        getSqlMapClientTemplate().insert("MN_H_BSC_TCH_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public MnHBscTchQos selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnHBscTchQos key = new MnHBscTchQos();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnHBscTchQos record = (MnHBscTchQos) getSqlMapClientTemplate().queryForObject("MN_H_BSC_TCH_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnHBscTchQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_TCH_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_TCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    public int updateByPrimaryKey(MnHBscTchQos record) {
        int rows = getSqlMapClientTemplate().update("MN_H_BSC_TCH_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}