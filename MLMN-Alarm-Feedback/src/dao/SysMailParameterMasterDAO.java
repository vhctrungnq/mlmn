package dao;

import java.util.List;

import vo.SYS_PARAMETER;
import vo.SysMailParameterMaster;

public interface SysMailParameterMasterDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    void insert(SysMailParameterMaster record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    void insertSelective(SysMailParameterMaster record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    SysMailParameterMaster selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    int updateByPrimaryKeySelective(SysMailParameterMaster record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER_MASTER
     *
     * @ibatorgenerated Thu Nov 21 17:25:34 ICT 2013
     */
    int updateByPrimaryKey(SysMailParameterMaster record);
    

	List<SYS_PARAMETER> titleForm(String typeForm);

	List<SysMailParameterMaster> getMailParameterMasterListAll(String mailLevel,String mailID);

	int updateMailParameterMaster(String email, String contentHeader,String contentFooter, String pathweb);

	SysMailParameterMaster getInformationMail(String pathweb);

}