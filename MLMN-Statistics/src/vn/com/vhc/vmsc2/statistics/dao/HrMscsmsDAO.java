package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscsms;

public interface HrMscsmsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    void insert(HrMscsms record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    void insertSelective(HrMscsms record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    HrMscsms selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscsms record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:09:07 ICT 2012
     */
    int updateByPrimaryKey(HrMscsms record);
    
    List<HrMscsms> filter(String startHour,Date startDate, String endHour, Date endDate, String mscid);
    List<HrMscsms> filter(String startHour, String endHour, Date endDate, String mscid);
}