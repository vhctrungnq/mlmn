package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;


import vn.com.vhc.vmsc2.statistics.domain.VRpipbbdata;

public interface VRpipbbdataDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_IPBB_DATA
     *
     * @ibatorgenerated Wed Nov 21 09:24:31 ICT 2012
     */
    void insert(VRpipbbdata record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_IPBB_DATA
     *
     * @ibatorgenerated Wed Nov 21 09:24:31 ICT 2012
     */
    void insertSelective(VRpipbbdata record);
    
    List<VRpipbbdata> filter(String module, String direction, String startDate, String endDate);
    List<VRpipbbdata> getList(String module, String direction, String startDate, String endDate);

	List<VRpipbbdata> filter1(String startDate, String endDate);
	List<VRpipbbdata> getBadLink(String date, String function);

}