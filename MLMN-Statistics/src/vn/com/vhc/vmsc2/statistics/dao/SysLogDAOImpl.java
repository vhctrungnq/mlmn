package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.SysLog;

public class SysLogDAOImpl extends SqlMapClientDaoSupport implements SysLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public SysLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        SysLog key = new SysLog();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("SYS_LOG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public void insert(SysLog record) {
        getSqlMapClientTemplate().insert("SYS_LOG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public void insertSelective(SysLog record) {
        getSqlMapClientTemplate().insert("SYS_LOG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public SysLog selectByPrimaryKey(Integer id) {
        SysLog key = new SysLog();
        key.setId(id);
        SysLog record = (SysLog) getSqlMapClientTemplate().queryForObject("SYS_LOG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public int updateByPrimaryKeySelective(SysLog record) {
        int rows = getSqlMapClientTemplate().update("SYS_LOG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOG
     *
     * @ibatorgenerated Tue May 21 14:43:00 ICT 2013
     */
    public int updateByPrimaryKey(SysLog record) {
        int rows = getSqlMapClientTemplate().update("SYS_LOG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}