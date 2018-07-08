package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwAcc;

public interface HrMscMgwAccDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    void insert(HrMscMgwAcc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    void insertSelective(HrMscMgwAcc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    HrMscMgwAcc selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscMgwAcc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    int updateByPrimaryKey(HrMscMgwAcc record);
    
    List<HrMscMgwAcc> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscMgwAcc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}