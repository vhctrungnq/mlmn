package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrSgsnQos;


public interface VRpHrSgsnQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SGSN_QOS
     *
     * @ibatorgenerated Wed Jun 22 15:29:43 ICT 2011
     */
    void insert(VRpHrSgsnQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SGSN_QOS
     *
     * @ibatorgenerated Wed Jun 22 15:29:43 ICT 2011
     */
    void insertSelective(VRpHrSgsnQos record);

    List<VRpHrSgsnQos> filter2(String startHour, Date startDate, String endHour, String region, String sgsnid);
    List<VRpHrSgsnQos> filter(String startHour, Date startDate, String endHour,Date endDate, String region, String sgsnid);
}