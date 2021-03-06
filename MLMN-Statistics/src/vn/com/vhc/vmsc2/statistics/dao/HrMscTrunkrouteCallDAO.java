package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscTrunkrouteCall;

public interface HrMscTrunkrouteCallDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid, String routeid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    void insert(HrMscTrunkrouteCall record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    void insertSelective(HrMscTrunkrouteCall record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    HrMscTrunkrouteCall selectByPrimaryKey(Date day, Integer hour, String mscid, String routeid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscTrunkrouteCall record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE
     *
     * @ibatorgenerated Wed Oct 17 16:15:56 ICT 2012
     */
    int updateByPrimaryKey(HrMscTrunkrouteCall record);
    
    List<HrMscTrunkrouteCall> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
    List<HrMscTrunkrouteCall> filter2(String startHour, Date startDate, String endHour, String mscid);
}