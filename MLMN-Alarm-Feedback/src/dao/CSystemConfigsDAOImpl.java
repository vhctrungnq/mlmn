package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CSystemConfigs;
import vo.SYS_PARAMETER;
public class CSystemConfigsDAOImpl extends SqlMapClientDaoSupport implements CSystemConfigsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public CSystemConfigsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public int deleteByPrimaryKey(String paramName) {
        CSystemConfigs key = new CSystemConfigs();
        key.setParamName(paramName);
        int rows = getSqlMapClientTemplate().delete("C_SYSTEM_CONFIGS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public void insert(CSystemConfigs record) {
        getSqlMapClientTemplate().insert("C_SYSTEM_CONFIGS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public void insertSelective(CSystemConfigs record) {
        getSqlMapClientTemplate().insert("C_SYSTEM_CONFIGS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public CSystemConfigs selectByPrimaryKey(String paramName) {
        CSystemConfigs key = new CSystemConfigs();
        key.setParamName(paramName);
        CSystemConfigs record = (CSystemConfigs) getSqlMapClientTemplate().queryForObject("C_SYSTEM_CONFIGS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public int updateByPrimaryKeySelective(CSystemConfigs record) {
        int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_SYSTEM_CONFIGS
     *
     * @ibatorgenerated Thu Feb 28 09:27:23 ICT 2013
     */
    public int updateByPrimaryKey(CSystemConfigs record) {
        int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleSystemConfig(String typeForm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_TYPE_FORM", typeForm);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.titleSystemConfig", map);
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> getConfigTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.getConfigTypeList", map);
	
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CSystemConfigs> getSystemConfigList(String paramName,
			String paramValue, String configType, String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_PARAM_NAME", paramName);
		map.put("P_PARAM_VALUE", paramValue);
		map.put("P_CONFIG_TYPE", configType);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.getSystemConfigList", map);
	}

	@Override
	public CSystemConfigs selectByID(Integer id) {
		CSystemConfigs key = new CSystemConfigs();
        key.setId(id);
        CSystemConfigs record = (CSystemConfigs) getSqlMapClientTemplate().queryForObject("C_SYSTEM_CONFIGS.selectByID", key);
        return record;
	}

	@Override
	public int deleteById(Integer id) {
		CSystemConfigs key = new CSystemConfigs();
		key.setId(id);
        int rows = getSqlMapClientTemplate().delete("C_SYSTEM_CONFIGS.deleteById", key);
        return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSystemConfigs> getSystemConfigMail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.getSystemConfigMail", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSystemConfigs> getSystemConfigMailDefault() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_SYSTEM_CONFIGS.getSystemConfigMailDefault", map);
	}

	@Override
	public int saveMail(String subject, String mailTo, String errorSend,
			String status,String mailId,String mailLevel,Integer hour) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_TITLE", subject);
		map.put("P_Email", mailTo);
		map.put("P_Status", status);
		map.put("P_Message", errorSend);
		map.put("P_Mail_Id", mailId);
		map.put("P_MAIL_LEVEL", mailLevel);
		map.put("P_hour", hour);
      	
		 int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.saveMail", map);
	       return rows;
	}

	@Override
	public int updateSystemConfigMail(String status, String delay,
			String startHour, String endHour) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_STATUS", status);
		map.put("P_DELAY", delay);
		map.put("P_START_HOUR", startHour);
		map.put("P_END_HOUR", endHour);
		 int rows = getSqlMapClientTemplate().update("C_SYSTEM_CONFIGS.updateSystemConfigMail", map);
	       return rows;
	}
}