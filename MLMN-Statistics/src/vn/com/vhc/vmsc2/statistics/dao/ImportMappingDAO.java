package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.ImportMapping;
import vn.com.vhc.vmsc2.statistics.web.filter.ImportMappingFilter;


public interface ImportMappingDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    int deleteByPrimaryKey(Integer patternId, String rawTable, String tableColumn);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    void insert(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    void insertSelective(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    ImportMapping selectByPrimaryKey(Integer patternId, String rawTable, String tableColumn);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    int updateByPrimaryKeySelective(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Wed Aug 25 01:37:33 ICT 2010
     */
    int updateByPrimaryKey(ImportMapping record);
    
    List<ImportMapping> getAll();

	List<ImportMapping> filter(ImportMappingFilter filter);

	ImportMapping exitsFilePattern(String rawTable, String tableColumn);
}