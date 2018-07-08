package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.ConvertLog;
import vo.SYS_PARAMETER;

public class ConvertLogDAOImpl extends SqlMapClientDaoSupport implements ConvertLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public ConvertLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public void insert(ConvertLog record) {
        getSqlMapClientTemplate().insert("I_FILE_CONVERT_LOGS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public void insertSelective(ConvertLog record) {
        getSqlMapClientTemplate().insert("I_FILE_CONVERT_LOGS.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleConvertLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("I_FILE_CONVERT_LOGS.titleConvertLog", map);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConvertLog> filter(String startDate, String endDate,
			String statusCode, String fileName, int startRecord, int endRecord,
			String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_START_DATE", startDate);
		map.put("P_END_DATE", endDate);
		map.put("P_STATUS_CODE", statusCode);
		map.put("P_FILE_NAME", fileName);
		map.put("P_START_RECORD", startRecord);
		map.put("P_END_RECORD", endRecord);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("I_FILE_CONVERT_LOGS.filter", map);
	}

	@Override
	public Integer countRow(String startDate, String endDate,
			String statusCode, String fileName) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_START_DATE", startDate);
		parms.put("P_END_DATE", endDate);
		parms.put("P_STATUS_CODE", statusCode);
		parms.put("P_FILE_NAME", fileName);
		parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("I_FILE_CONVERT_LOGS.countRow", parms);
    	Integer record = (Integer) parms.get("P_DATA");
    	return record;
	}

	
}