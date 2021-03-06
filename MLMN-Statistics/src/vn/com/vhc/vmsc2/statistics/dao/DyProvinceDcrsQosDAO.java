package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyProvinceDcrsQos;


public interface DyProvinceDcrsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_PROVINCE_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 09:45:03 ICT 2011
     */
    void insert(DyProvinceDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_PROVINCE_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 09:45:03 ICT 2011
     */
    void insertSelective(DyProvinceDcrsQos record);
    
    List<DyProvinceDcrsQos> filter(String startDate, String endDate, String province, String region);
}