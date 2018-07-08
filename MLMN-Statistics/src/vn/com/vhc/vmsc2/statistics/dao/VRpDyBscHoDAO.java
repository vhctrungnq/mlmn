package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHo;


public interface VRpDyBscHoDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:40 ICT 2010
     */
    void insert(VRpDyBscHo record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:40 ICT 2010
     */
    void insertSelective(VRpDyBscHo record);

	List<VRpDyBscHo> filterDetails(String startDate, String endDate, String bscid, String region);

	List<VRpDyBscHo> filter(String bscid, Date d, String region);
	
	List<VRpDyBscHo> filterLS(String startDate, String endDate, String bscid);
}