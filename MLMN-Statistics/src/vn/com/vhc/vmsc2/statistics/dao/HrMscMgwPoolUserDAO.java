package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwPoolUser;

public interface HrMscMgwPoolUserDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid, String userlabel);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    void insert(HrMscMgwPoolUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    void insertSelective(HrMscMgwPoolUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    HrMscMgwPoolUser selectByPrimaryKey(Date day, Integer hour, String mscid, String userlabel);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscMgwPoolUser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_POOL_USER
     *
     * @ibatorgenerated Tue Oct 23 10:29:23 ICT 2012
     */
    int updateByPrimaryKey(HrMscMgwPoolUser record);
    
    List<HrMscMgwPoolUser> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscMgwPoolUser> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}