package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;

public interface HrBscCoreDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    void insert(HrBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    void insertSelective(HrBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    HrBscCore selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    int updateByPrimaryKeySelective(HrBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_CORE
     *
     * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
     */
    int updateByPrimaryKey(HrBscCore record);
    List<HrBscCore> filterDayHourAndBsc(Date day,Integer hour, String bscid);

	List<HrBscCore> filter(String bscid, String day, Integer startHour, Integer endHour);
}