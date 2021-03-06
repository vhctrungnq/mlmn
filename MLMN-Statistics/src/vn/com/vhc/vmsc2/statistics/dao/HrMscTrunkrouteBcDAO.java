package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscTrunkrouteBc;

public interface HrMscTrunkrouteBcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    void insert(HrMscTrunkrouteBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    void insertSelective(HrMscTrunkrouteBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    HrMscTrunkrouteBc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscTrunkrouteBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    int updateByPrimaryKey(HrMscTrunkrouteBc record);
    
    List<HrMscTrunkrouteBc> filter(String startHour, Date startDate, String endHour,Date endDate, String routeid);
    List<HrMscTrunkrouteBc> filter2(String startHour, Date startDate, String endHour, String routeid);
}