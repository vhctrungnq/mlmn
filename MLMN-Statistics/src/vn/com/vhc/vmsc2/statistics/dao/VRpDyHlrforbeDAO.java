package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrforbe;

public interface VRpDyHlrforbeDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_BE
     *
     * @ibatorgenerated Tue Nov 27 14:29:06 ICT 2012
     */
    void insert(VRpDyHlrforbe record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_BE
     *
     * @ibatorgenerated Tue Nov 27 14:29:06 ICT 2012
     */
    void insertSelective(VRpDyHlrforbe record);
    
    List<VRpDyHlrforbe> filter(String startDate, String endDate, String nodeid);
}