package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBsc3gQos;

public interface VRpMnBsc3gQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:11 ICT 2012
     */
    void insert(VRpMnBsc3gQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:11 ICT 2012
     */
    void insertSelective(VRpMnBsc3gQos record);
    //RNC Accessibility
    List<VRpMnBsc3gQos> filterAccessibility(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String mscid, String region);
    //RNC Dropper Rate
    List<VRpMnBsc3gQos> filter1(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String mscid, String region);
    //RNC Blocking Rate
    List<VRpMnBsc3gQos> filter2(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String mscid, String region);
    //RNC Call Completion
    List<VRpMnBsc3gQos> filter3(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String mscid, String region);
    //RNC Intergrity
    List<VRpMnBsc3gQos> filter4(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String mscid, String region);
}