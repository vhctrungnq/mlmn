package dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.SysSend;

public class SysSendDAOImpl extends SqlMapClientDaoSupport implements SysSendDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public SysSendDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        SysSend key = new SysSend();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("SYS_SEND.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void insert(SysSend record) {
        getSqlMapClientTemplate().insert("SYS_SEND.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void insertSelective(SysSend record) {
        getSqlMapClientTemplate().insert("SYS_SEND.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public SysSend selectByPrimaryKey(Integer id) {
        SysSend key = new SysSend();
        key.setId(id);
        SysSend record = (SysSend) getSqlMapClientTemplate().queryForObject("SYS_SEND.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public int updateByPrimaryKeySelective(SysSend record) {
        int rows = getSqlMapClientTemplate().update("SYS_SEND.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public int updateByPrimaryKey(SysSend record) {
        int rows = getSqlMapClientTemplate().update("SYS_SEND.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}