package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.ServerDetail;

public class ServerDetailDAOImpl extends SqlMapClientDaoSupport implements ServerDetailDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public ServerDetailDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public int deleteByPrimaryKey(String baseDir, Integer serverId) {
        ServerDetail key = new ServerDetail();
        key.setBaseDir(baseDir);
        key.setServerId(serverId);
        int rows = getSqlMapClientTemplate().delete("C_SERVERS_DETAIL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public void insert(ServerDetail record) {
        getSqlMapClientTemplate().insert("C_SERVERS_DETAIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public void insertSelective(ServerDetail record) {
        getSqlMapClientTemplate().insert("C_SERVERS_DETAIL.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public ServerDetail selectByPrimaryKey(String baseDir, Integer serverId) {
        ServerDetail key = new ServerDetail();
        key.setBaseDir(baseDir);
        key.setServerId(serverId);
        ServerDetail record = (ServerDetail) getSqlMapClientTemplate().queryForObject("C_SERVERS_DETAIL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public int updateByPrimaryKeySelective(ServerDetail record) {
        int rows = getSqlMapClientTemplate().update("C_SERVERS_DETAIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SERVERS_DETAIL
     *
     * @ibatorgenerated Sat Sep 11 19:09:12 ICT 2010
     */
    public int updateByPrimaryKey(ServerDetail record) {
        int rows = getSqlMapClientTemplate().update("C_SERVERS_DETAIL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<ServerDetail> getByServerId(Integer serverId) {
		return getSqlMapClientTemplate().queryForList("C_SERVERS_DETAIL.getByServerId", serverId);
	}
}