package dao;

import java.util.List;

import vo.ImportMapping;
import vo.SYS_PARAMETER;
import vo.alarm.utils.ImportMappingFilter;

public interface ImportMappingDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    int deleteByPrimaryKey(Integer patternId, String rawTable, String tableColumn);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    void insert(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    void insertSelective(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    ImportMapping selectByPrimaryKey(Integer patternId, String rawTable, String tableColumn);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    int updateByPrimaryKeySelective(ImportMapping record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_IMPORT_MAPPING
     *
     * @ibatorgenerated Thu Feb 21 16:03:45 ICT 2013
     */
    int updateByPrimaryKey(ImportMapping record);

	

	List<SYS_PARAMETER> titleImportMapping(String object);

	List<ImportMapping> getImportMappingList(ImportMappingFilter filter,
			String column, int order);
}