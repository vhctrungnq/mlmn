package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBranch;


public interface VRpDyBranchDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH
     *
     * @ibatorgenerated Mon Feb 21 14:31:48 ICT 2011
     */
    void insert(VRpDyBranch record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH
     *
     * @ibatorgenerated Mon Feb 21 14:31:48 ICT 2011
     */
    void insertSelective(VRpDyBranch record);

	List<VRpDyBranch> filter(String startDate, String endDate, String branch, String region);
}