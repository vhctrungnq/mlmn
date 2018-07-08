package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwSccpUser;

public interface HrMscMgwSccpUserDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid, String userlabel);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    void insert(HrMscMgwSccpUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    void insertSelective(HrMscMgwSccpUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    HrMscMgwSccpUser selectByPrimaryKey(Date day, Integer hour, String mscid, String userlabel);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscMgwSccpUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    int updateByPrimaryKey(HrMscMgwSccpUser record);
    
    List<HrMscMgwSccpUser> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscMgwSccpUser> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}