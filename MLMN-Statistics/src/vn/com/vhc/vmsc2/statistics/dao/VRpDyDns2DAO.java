package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyDns2;

public interface VRpDyDns2DAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DNS_2
     *
     * @ibatorgenerated Wed Aug 06 17:14:05 ICT 2014
     */
    void insert(VRpDyDns2 record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DNS_2
     *
     * @ibatorgenerated Wed Aug 06 17:14:05 ICT 2014
     */
    void insertSelective(VRpDyDns2 record);
    List<VRpDyDns2> filterDaily(String tableName, String mscid,String startDate, String endDate);

}