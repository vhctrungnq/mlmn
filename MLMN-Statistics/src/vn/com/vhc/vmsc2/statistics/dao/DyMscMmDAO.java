package vn.com.vhc.vmsc2.statistics.dao;

import java.sql.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMm;

public interface DyMscMmDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    void insert(DyMscMm record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    void insertSelective(DyMscMm record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    DyMscMm selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMm record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MM
     *
     * @ibatorgenerated Tue Oct 23 16:04:02 ICT 2012
     */
    int updateByPrimaryKey(DyMscMm record);
    
    List<DyMscMm> filter( String mscid,String startDate, String endDate);
}