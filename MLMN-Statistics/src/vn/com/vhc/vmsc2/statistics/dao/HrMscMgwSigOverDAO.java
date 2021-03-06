package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwSigOver;

public interface HrMscMgwSigOverDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    void insert(HrMscMgwSigOver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    void insertSelective(HrMscMgwSigOver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    HrMscMgwSigOver selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscMgwSigOver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Mon Oct 22 17:13:21 ICT 2012
     */
    int updateByPrimaryKey(HrMscMgwSigOver record);
    
    List<HrMscMgwSigOver> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscMgwSigOver> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}