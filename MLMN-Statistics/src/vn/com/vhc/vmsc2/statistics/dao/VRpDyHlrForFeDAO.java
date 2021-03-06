package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForFe;

public interface VRpDyHlrForFeDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 15:20:13 ICT 2012
     */
    void insert(VRpDyHlrForFe record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 15:20:13 ICT 2012
     */
    void insertSelective(VRpDyHlrForFe record);
    
    List<VRpDyHlrForFe> filter(String startDate, String endDate, String nodeid);
    List<VRpDyHlrForFe> filterDay(String startDate, String endDate);
    
}