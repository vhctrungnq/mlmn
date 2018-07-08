package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.SysParameter;

public interface SysParameterDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    void insert(SysParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    void insertSelective(SysParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    SysParameter selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    int updateByPrimaryKeySelective(SysParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Wed Sep 26 17:46:03 ICT 2012
     */
    int updateByPrimaryKey(SysParameter record);
    
    List<SysParameter> filter(String code);
    
    List<SysParameter> filter(String code, String name);
   
}