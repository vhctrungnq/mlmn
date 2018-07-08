package dao;

import java.util.List;

import vo.SysFiles;

public interface SysFilesDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    void insert(SysFiles record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    void insertSelective(SysFiles record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    SysFiles selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    int updateByPrimaryKeySelective(SysFiles record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FILES
     *
     * @ibatorgenerated Wed Oct 09 11:59:41 PDT 2013
     */
    int updateByPrimaryKey(SysFiles record);

	int deleteSysFilesByMappingId(Integer id);

	int insertSelectiveAndReturn(SysFiles record);

	List<SysFiles> getSysFilesByMapping(String mappingId);
}