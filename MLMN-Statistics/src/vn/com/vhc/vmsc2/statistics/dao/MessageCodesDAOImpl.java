package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MessageCodes;
import vn.com.vhc.vmsc2.statistics.web.filter.MessageCodesFilter;

public class MessageCodesDAOImpl extends SqlMapClientDaoSupport implements MessageCodesDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public MessageCodesDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public int deleteByPrimaryKey(String msgCode) {
        MessageCodes key = new MessageCodes();
        key.setMsgCode(msgCode);
        int rows = getSqlMapClientTemplate().delete("C_MESSAGE_CODES.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public void insert(MessageCodes record) {
        getSqlMapClientTemplate().insert("C_MESSAGE_CODES.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public void insertSelective(MessageCodes record) {
        getSqlMapClientTemplate().insert("C_MESSAGE_CODES.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public MessageCodes selectByPrimaryKey(String msgCode) {
        MessageCodes key = new MessageCodes();
        key.setMsgCode(msgCode);
        MessageCodes record = (MessageCodes) getSqlMapClientTemplate().queryForObject("C_MESSAGE_CODES.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public int updateByPrimaryKeySelective(MessageCodes record) {
        int rows = getSqlMapClientTemplate().update("C_MESSAGE_CODES.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_MESSAGE_CODES
     *
     * @ibatorgenerated Fri Dec 03 10:48:36 ICT 2010
     */
    public int updateByPrimaryKey(MessageCodes record) {
        int rows = getSqlMapClientTemplate().update("C_MESSAGE_CODES.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    @SuppressWarnings("unchecked")
	public List<MessageCodes> filter(MessageCodesFilter filter){
    	return getSqlMapClientTemplate().queryForList("C_MESSAGE_CODES.filter",filter);
    }
}