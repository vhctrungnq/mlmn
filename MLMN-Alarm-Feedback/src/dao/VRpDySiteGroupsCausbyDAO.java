package dao;

import java.util.List;

import vo.VRpDySiteGroups;
import vo.VRpDySiteGroupsCausby;

public interface VRpDySiteGroupsCausbyDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_GROUPS_CAUSBY
     *
     * @ibatorgenerated Fri Aug 29 14:52:31 ICT 2014
     */
    void insert(VRpDySiteGroupsCausby record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_GROUPS_CAUSBY
     *
     * @ibatorgenerated Fri Aug 29 14:52:31 ICT 2014
     */
    void insertSelective(VRpDySiteGroupsCausby record);
    List<VRpDySiteGroupsCausby> getInfo(String startDate, String endDate );
}