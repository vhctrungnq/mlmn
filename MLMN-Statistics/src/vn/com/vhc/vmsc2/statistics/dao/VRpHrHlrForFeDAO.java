package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrForFe;

public interface VRpHrHlrForFeDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 11:32:19 ICT 2012
     */
    void insert(VRpHrHlrForFe record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 11:32:19 ICT 2012
     */
    void insertSelective(VRpHrHlrForFe record);
    
    List<VRpHrHlrForFe> filter2(String startHour, Date startDate, String endHour, String nodeid);

	List<VRpHrHlrForFe> filter(String startHour, Date startDate, String endHour, Date endDate, String nodeid);
}