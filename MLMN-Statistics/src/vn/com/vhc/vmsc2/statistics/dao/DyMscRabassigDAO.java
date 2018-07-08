package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscRabassig;

public interface DyMscRabassigDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    void insert(DyMscRabassig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    void insertSelective(DyMscRabassig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    DyMscRabassig selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscRabassig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    int updateByPrimaryKey(DyMscRabassig record);
    
    List<DyMscRabassig> filter( String mscid,String startDate, String endDate);
}