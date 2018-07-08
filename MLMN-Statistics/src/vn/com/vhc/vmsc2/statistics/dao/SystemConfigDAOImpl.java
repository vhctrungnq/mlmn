package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.SystemConfig;
import vn.com.vhc.vmsc2.statistics.web.filter.SystemConfigFilter;

public class SystemConfigDAOImpl extends SqlMapClientDaoSupport implements SystemConfigDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public SystemConfigDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public int deleteByPrimaryKey(Integer id) {
        SystemConfig key = new SystemConfig();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("C_SYSTEM_CONFIGS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public void insert(SystemConfig record) {
        getSqlMapClientTemplate().insert("C_SYSTEM_CONFIGS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public void insertSelective(SystemConfig record) {
        getSqlMapClientTemplate().insert("C_SYSTEM_CONFIGS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public SystemConfig selectByPrimaryKey(Integer id) {
        SystemConfig key = new SystemConfig();
        key.setId(id);
        SystemConfig record = (SystemConfig) getSqlMapClientTemplate().queryForObject("C_SYSTEM_CONFIGS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public int updateByPrimaryKeySelective(SystemConfig record) {
        int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Wed Aug 25 10:05:59 ICT 2010
     */
    public int updateByPrimaryKey(SystemConfig record) {
        int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<SystemConfig> getAll() {
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.getAll");
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemConfig> filter(SystemConfigFilter filter) {
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.filter",filter);
	}
	
	public SystemConfig exitSystemConfig(String paramName) 
	{
		SystemConfig key = new SystemConfig();
		key.setParamName(paramName);
		SystemConfig record = (SystemConfig) getSqlMapClientTemplate().queryForObject("C_SYSTEM_CONFIGS.exitSystemConfig", key);
		
			return record;
	}
}