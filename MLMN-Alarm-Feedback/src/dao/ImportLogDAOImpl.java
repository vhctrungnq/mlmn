package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.ImportLog;
import vo.SYS_PARAMETER;

public class ImportLogDAOImpl extends SqlMapClientDaoSupport implements ImportLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_IMPORT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public ImportLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_IMPORT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void insert(ImportLog record) {
        getSqlMapClientTemplate().insert("I_FILE_IMPORT_LOGS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_IMPORT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 16:31:17 ICT 2010
     */
    public void insertSelective(ImportLog record) {
        getSqlMapClientTemplate().insert("I_FILE_IMPORT_LOGS.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
	@Override
	public List<ImportLog> filter(String startDate, String endDate,
			String statusCode, String fileName, int startRecord, int endRecord, String column,
			int order) {
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
		return getSqlMapClientTemplate().queryForList("I_FILE_IMPORT_LOGS.filter", map);
	
	}

	@Override
	public Integer countRow(String startDate, String endDate, String statusCode,String fileName) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_START_DATE", startDate);
		parms.put("P_END_DATE", endDate);
		parms.put("P_STATUS_CODE", statusCode);
		parms.put("P_FILE_NAME", fileName);
		parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("I_FILE_IMPORT_LOGS.countRow", parms);
    	Integer record = (Integer) parms.get("P_DATA");
    	return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleImportLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("I_FILE_IMPORT_LOGS.titleImportLog", map);

	}
}