package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VrpMnIpbbLink1;

public interface VrpMnIpbbLink1DAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_IPBB_LINK1
     *
     * @ibatorgenerated Mon Feb 04 11:51:20 ICT 2013
     */
    void insert(VrpMnIpbbLink1 record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_IPBB_LINK1
     *
     * @ibatorgenerated Mon Feb 04 11:51:20 ICT 2013
     */
    void insertSelective(VrpMnIpbbLink1 record);
    
    List<VrpMnIpbbLink1> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);
}