package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHsrOulQos;


public interface VRpDyBscHsrOulQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HSR_OUL_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:00 ICT 2011
     */
    void insert(VRpDyBscHsrOulQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HSR_OUL_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:00 ICT 2011
     */
    void insertSelective(VRpDyBscHsrOulQos record);
    List<VRpDyBscHsrOulQos> filter(String startDate, String endDate, String bscid, String region);
}