package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscMgwSummary;

public interface VRpDyMscMgwSummaryDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_MGW_SUMMARY
     *
     * @ibatorgenerated Tue Jan 08 10:57:24 ICT 2013
     */
    void insert(VRpDyMscMgwSummary record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_MGW_SUMMARY
     *
     * @ibatorgenerated Tue Jan 08 10:57:24 ICT 2013
     */
    void insertSelective(VRpDyMscMgwSummary record);
    List<VRpDyMscMgwSummary> filterCSCoreMGW(String startDate, String endDate);
}