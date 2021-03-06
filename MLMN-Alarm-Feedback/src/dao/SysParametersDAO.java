package dao;

import java.util.List;

import vo.SysParameters;

public interface SysParametersDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    void insert(SysParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    void insertSelective(SysParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    SysParameters selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    int updateByPrimaryKeySelective(SysParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETERS
     *
     * @ibatorgenerated Thu Mar 10 16:41:57 ICT 2016
     */
    int updateByPrimaryKey(SysParameters record);
    
    List<SysParameters> filter(String code);
}