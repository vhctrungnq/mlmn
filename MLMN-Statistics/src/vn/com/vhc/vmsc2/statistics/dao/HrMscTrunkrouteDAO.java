package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscTrunkroute;

public interface HrMscTrunkrouteDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    void insert(HrMscTrunkroute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    void insertSelective(HrMscTrunkroute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    HrMscTrunkroute selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscTrunkroute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK
     *
     * @ibatorgenerated Mon Oct 15 10:27:09 ICT 2012
     */
    int updateByPrimaryKey(HrMscTrunkroute record);
    
    List<HrMscTrunkroute> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
    List<HrMscTrunkroute> filter2(String startHour, Date startDate, String endHour, String mscid);
}