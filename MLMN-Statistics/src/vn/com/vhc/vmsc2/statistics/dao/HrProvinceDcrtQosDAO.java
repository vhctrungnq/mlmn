package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrProvinceDcrtQos;


public interface HrProvinceDcrtQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_DCRT_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:07:19 ICT 2011
     */
    void insert(HrProvinceDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_DCRT_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:07:19 ICT 2011
     */
    void insertSelective(HrProvinceDcrtQos record);
    
    List<HrProvinceDcrtQos> filter(String startHour, String endHour, Date day, String province, String region);
}