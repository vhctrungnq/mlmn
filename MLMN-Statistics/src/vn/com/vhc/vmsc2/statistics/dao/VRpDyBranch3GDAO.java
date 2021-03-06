package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBranch3G;


public interface VRpDyBranch3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    void insert(VRpDyBranch3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    void insertSelective(VRpDyBranch3G record);

	List<VRpDyBranch3G> filter(String startDate, String endDate, String branch, String region);
}