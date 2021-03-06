package dao;

import java.util.List;

import vo.SysMailParameter;

public interface SysMailParameterDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER
     *
     * @ibatorgenerated Thu Nov 21 17:25:42 ICT 2013
     */
    void insert(SysMailParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_MAIL_PARAMETER
     *
     * @ibatorgenerated Thu Nov 21 17:25:42 ICT 2013
     */
    void insertSelective(SysMailParameter record);

    //lay danh sach cac block cua mails
	List<SysMailParameter> getBlockListReport(String mailID, String blockID);
}