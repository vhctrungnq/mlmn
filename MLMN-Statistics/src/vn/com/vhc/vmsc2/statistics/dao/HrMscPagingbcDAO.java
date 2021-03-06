package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscPagingbc;

public interface HrMscPagingbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    void insert(HrMscPagingbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    void insertSelective(HrMscPagingbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    HrMscPagingbc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    int updateByPrimaryKeySelective(HrMscPagingbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_BC
     *
     * @ibatorgenerated Sat Oct 13 10:29:35 ICT 2012
     */
    int updateByPrimaryKey(HrMscPagingbc record);
    
    List<HrMscPagingbc> filter2(String startHour, Date startDate, String endHour, String mscid);
    List<HrMscPagingbc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid);
}