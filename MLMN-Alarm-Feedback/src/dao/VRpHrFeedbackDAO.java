package dao;

import java.util.List;

import vo.VRpHrFeedback;

public interface VRpHrFeedbackDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_FEEDBACK
     *
     * @ibatorgenerated Thu Oct 06 14:57:03 ICT 2016
     */
    void insert(VRpHrFeedback record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_FEEDBACK
     *
     * @ibatorgenerated Thu Oct 06 14:57:03 ICT 2016
     */
    void insertSelective(VRpHrFeedback record);
    
    List<VRpHrFeedback> getDataForChart(String date, String province);
}