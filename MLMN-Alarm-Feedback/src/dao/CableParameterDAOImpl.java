package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CableParameter;
import vo.SYS_PARAMETER;

public class CableParameterDAOImpl extends SqlMapClientDaoSupport implements CableParameterDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public CableParameterDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        CableParameter key = new CableParameter();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_PARAMETER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public void insert(CableParameter record) {
        getSqlMapClientTemplate().insert("CABLE_PARAMETER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public void insertSelective(CableParameter record) {
        getSqlMapClientTemplate().insert("CABLE_PARAMETER.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public CableParameter selectByPrimaryKey(Integer id) {
        CableParameter key = new CableParameter();
        key.setId(id);
        CableParameter record = (CableParameter) getSqlMapClientTemplate().queryForObject("CABLE_PARAMETER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public int updateByPrimaryKeySelective(CableParameter record) {
        int rows = getSqlMapClientTemplate().update("CABLE_PARAMETER.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    public int updateByPrimaryKey(CableParameter record) {
        int rows = getSqlMapClientTemplate().update("CABLE_PARAMETER.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@Override
	public List<CableParameter> getCabParameterValue(String focus, String term) {
		
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("focus", 			focus);
    	parms.put("term", 			term);
		parms.put("P_DATA", null);
    	
		@SuppressWarnings("unchecked")
		List<CableParameter> record = getSqlMapClientTemplate().queryForList("CABLE_PARAMETER.getCabParameterValue", parms);
        return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TYPEFORM", typeForm);
		parms.put("P_DATA", null);
		
    	return getSqlMapClientTemplate().queryForList("CABLE_PARAMETER.titleForm", parms);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CableParameter> distinctMaThamSo() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		
    	return getSqlMapClientTemplate().queryForList("CABLE_PARAMETER.distinctMaThamSo", parms);
	}

	@Override
	public Object getSysParametersFilter(String code, String name,
			String column, String order) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_CODE", code);
    	parms.put("P_NAME", name);
    	parms.put("P_COLUMN", column);
		parms.put("P_ORDER", order);
		parms.put("P_DATA", null);
		
    	return getSqlMapClientTemplate().queryForList("CABLE_PARAMETER.getSysParametersFilter", parms);
	}
}