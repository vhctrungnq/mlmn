package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBsc3gQos;

public interface VRpHrBsc3gQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_3G_QOS
     *
     * @ibatorgenerated Fri Aug 17 14:32:33 ICT 2012
     */
    void insert(VRpHrBsc3gQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_3G_QOS
     *
     * @ibatorgenerated Fri Aug 17 14:32:33 ICT 2012
     */
    void insertSelective(VRpHrBsc3gQos record);
    
    //RNC Accessibility
    List<VRpHrBsc3gQos> filterAccessibility1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region);
    List<VRpHrBsc3gQos> filterAccessibility2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region);
    
    //RNC Dropper Rate
    List<VRpHrBsc3gQos> filterDropRate1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region);
    List<VRpHrBsc3gQos> filterDropRate2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region);
    
    //RNC Blocking Rate
    List<VRpHrBsc3gQos> filterBlocking1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region);
    List<VRpHrBsc3gQos> filterBlocking2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region);
    
    //RNC Call Completion
    List<VRpHrBsc3gQos> filterCallCompletion1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region);
    List<VRpHrBsc3gQos> filterCallCompletion2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region);
    
  //RNC Intergrity
    List<VRpHrBsc3gQos> filterIntergrity1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region);
    List<VRpHrBsc3gQos> filterIntergrity2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region);
}