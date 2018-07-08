package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscPagingArea;

public interface HrMscPagingAreaDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    void insert(HrMscPagingArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    void insertSelective(HrMscPagingArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    HrMscPagingArea selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscPagingArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    int updateByPrimaryKey(HrMscPagingArea record);
    
    List<HrMscPagingArea> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscPagingArea> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}