package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBranch3G;


public interface VRpHrBranch3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BRANCH_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    void insert(VRpHrBranch3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BRANCH_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    void insertSelective(VRpHrBranch3G record);

	List<VRpHrBranch3G> filter(String startHour, String endHour, String day, String branch, String region);
}