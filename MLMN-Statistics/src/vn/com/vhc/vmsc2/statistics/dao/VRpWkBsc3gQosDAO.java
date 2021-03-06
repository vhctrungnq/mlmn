package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBsc3gQos;

public interface VRpWkBsc3gQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:40 ICT 2012
     */
    void insert(VRpWkBsc3gQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:40 ICT 2012
     */
    void insertSelective(VRpWkBsc3gQos record);
    //RNC Accessibility
    List<VRpWkBsc3gQos> filterAccessibility(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String mscid,String region);
    //RNC Dropper Rate
    List<VRpWkBsc3gQos> filter1(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String mscid, String region);
    //RNC Blocking Rate
    List<VRpWkBsc3gQos> filter2(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String mscid, String region);
    //RNC Call Completion
    List<VRpWkBsc3gQos> filter3(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String mscid, String region);
  //RNC Intergrity
    List<VRpWkBsc3gQos> filter4(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String mscid, String region);
}