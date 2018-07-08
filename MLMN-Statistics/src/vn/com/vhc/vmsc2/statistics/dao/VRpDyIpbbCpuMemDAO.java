package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbCpuMem;

public interface VRpDyIpbbCpuMemDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_CPU_MEM
     *
     * @ibatorgenerated Wed Dec 25 15:44:20 ICT 2013
     */
    void insert(VRpDyIpbbCpuMem record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_CPU_MEM
     *
     * @ibatorgenerated Wed Dec 25 15:44:20 ICT 2013
     */
    void insertSelective(VRpDyIpbbCpuMem record);
    
    List<VRpDyIpbbCpuMem> filterDaily(String tableName, String linkid, String startDate, String endDate);
    List<VRpDyIpbbCpuMem> filterHourly(String tableName, String linkid, String startDate, String endDate);
    List<VRpDyIpbbCpuMem> getLinkList(String startDate, String endDate);
    
}