package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrProvinceDcrsQos;


public interface HrProvinceDcrsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_PROVINCE_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 09:53:21 ICT 2011
     */
    void insert(HrProvinceDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_PROVINCE_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 09:53:21 ICT 2011
     */
    void insertSelective(HrProvinceDcrsQos record);
    
    List<HrProvinceDcrsQos> filter(String startHour, String endHour, Date day, String province, String region);
}