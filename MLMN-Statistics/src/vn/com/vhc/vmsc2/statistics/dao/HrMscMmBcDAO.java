package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMmBc;

public interface HrMscMmBcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    void insert(HrMscMmBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    void insertSelective(HrMscMmBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    HrMscMmBc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscMmBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    int updateByPrimaryKey(HrMscMmBc record);
    
    List<HrMscMmBc> filter(String startHour, Date startDate, String endHour, Date endDate, String mscid);

	List<HrMscMmBc> filter2(String startHour, Date startDate, String endHour, String mscid);
}