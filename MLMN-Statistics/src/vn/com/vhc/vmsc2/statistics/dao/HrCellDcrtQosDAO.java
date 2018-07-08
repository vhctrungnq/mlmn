package vn.com.vhc.vmsc2.statistics.dao;


import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrCellDcrtQos;

public interface HrCellDcrtQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    int deleteByPrimaryKey(String bscid, String cellid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    void insert(HrCellDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    void insertSelective(HrCellDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    HrCellDcrtQos selectByPrimaryKey(String bscid, String cellid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    int updateByPrimaryKeySelective(HrCellDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_DCRT_QOS
     *
     * @ibatorgenerated Wed Jan 12 16:47:59 ICT 2011
     */
    int updateByPrimaryKey(HrCellDcrtQos record);

	List<HrCellDcrtQos> filter(String startHour, String endHour, Date day, String bscid, String cellid);
}