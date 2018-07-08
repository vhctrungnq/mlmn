package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscPagingAreaBc;

public interface HrMscPagingAreaBcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    void insert(HrMscPagingAreaBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    void insertSelective(HrMscPagingAreaBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    HrMscPagingAreaBc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscPagingAreaBc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA_OBJ_BC
     *
     * @ibatorgenerated Mon Oct 15 15:46:39 ICT 2012
     */
    int updateByPrimaryKey(HrMscPagingAreaBc record);
    
    List<HrMscPagingAreaBc> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscPagingAreaBc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}