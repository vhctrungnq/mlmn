package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.ImportMapping;
import vn.com.vhc.vmsc2.statistics.web.filter.ImportMappingFilter;

public class ImportMappingDAOImpl extends SqlMapClientDaoSupport implements ImportMappingDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public ImportMappingDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public int deleteByPrimaryKey(Integer patternId, String rawTable, String tableColumn) {
        ImportMapping key = new ImportMapping();
        key.setPatternId(patternId);
        key.setRawTable(rawTable);
        key.setTableColumn(tableColumn);
        int rows = getSqlMapClientTemplate().delete("I_IMPORT_MAPPING.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public void insert(ImportMapping record) {
        getSqlMapClientTemplate().insert("I_IMPORT_MAPPING.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public void insertSelective(ImportMapping record) {
        getSqlMapClientTemplate().insert("I_IMPORT_MAPPING.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public ImportMapping selectByPrimaryKey(Integer patternId, String rawTable, String tableColumn) {
        ImportMapping key = new ImportMapping();
        key.setPatternId(patternId);
        key.setRawTable(rawTable);
        key.setTableColumn(tableColumn);
        ImportMapping record = (ImportMapping) getSqlMapClientTemplate().queryForObject("I_IMPORT_MAPPING.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public int updateByPrimaryKeySelective(ImportMapping record) {
        int rows = getSqlMapClientTemplate().update("I_IMPORT_MAPPING.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    public int updateByPrimaryKey(ImportMapping record) {
        int rows = getSqlMapClientTemplate().update("I_IMPORT_MAPPING.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    @SuppressWarnings("unchecked")
	public List<ImportMapping> getAll() {
		return getSqlMapClientTemplate().queryForList("I_IMPORT_MAPPING.getAll");
	}

	@SuppressWarnings("unchecked")
	public List<ImportMapping> filter(ImportMappingFilter filter) {
		return getSqlMapClientTemplate().queryForList("I_IMPORT_MAPPING.filter", filter);
	}

	public ImportMapping exitsFilePattern(String rawTable, String tableColumn) {
		ImportMapping key = new ImportMapping();
        key.setFilePattern(rawTable);
        key.setFilePattern(tableColumn);
        ImportMapping record = (ImportMapping) getSqlMapClientTemplate().queryForObject("I_IMPORT_MAPPING.exitsFilePattern", key);
        return record;
	}
}