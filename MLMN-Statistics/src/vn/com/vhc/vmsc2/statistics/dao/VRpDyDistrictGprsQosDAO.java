package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictGprsQos;


public interface VRpDyDistrictGprsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_GPRS_QOS
     *
     * @ibatorgenerated Tue Dec 21 10:39:25 ICT 2010
     */
    void insert(VRpDyDistrictGprsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_GPRS_QOS
     *
     * @ibatorgenerated Tue Dec 21 10:39:25 ICT 2010
     */
    void insertSelective(VRpDyDistrictGprsQos record);

	List<VRpDyDistrictGprsQos> filterDetails(String startDate, String endDate, String province, String district, String region);
}