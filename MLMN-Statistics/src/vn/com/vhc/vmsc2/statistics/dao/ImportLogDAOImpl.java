package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.ImportLog;
import vn.com.vhc.vmsc2.statistics.web.filter.FileControlFilter;

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

	/*@SuppressWarnings("unchecked")
	public List<ImportLog> filter(TimeLog filter) {
		return getSqlMapClientTemplate().queryForList("I_FILE_IMPORT_LOGS.filter", filter);
	}

	public Integer countRow(TimeLog filter) {
		return (Integer) getSqlMapClientTemplate().queryForObject("I_FILE_IMPORT_LOGS.countRow", filter);
	}*/
    @SuppressWarnings("unchecked")
	public List<ImportLog> filter(FileControlFilter filter) {
		return getSqlMapClientTemplate().queryForList("I_FILE_IMPORT_LOGS.filter", filter);
	}

	public Integer countRow(FileControlFilter filter) {
		return (Integer) getSqlMapClientTemplate().queryForObject("I_FILE_IMPORT_LOGS.countRow", filter);
	}
}