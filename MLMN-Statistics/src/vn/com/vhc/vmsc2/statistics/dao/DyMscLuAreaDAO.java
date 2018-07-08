package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscLuArea;

public interface DyMscLuAreaDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    void insert(DyMscLuArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    void insertSelective(DyMscLuArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    DyMscLuArea selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscLuArea record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LU_AREA
     *
     * @ibatorgenerated Fri Oct 12 11:26:05 ICT 2012
     */
    int updateByPrimaryKey(DyMscLuArea record);
    
    List<DyMscLuArea> filter( String mscid,String startDate, String endDate);
}