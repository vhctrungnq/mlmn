package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwRetain;

public interface DyMscMgwRetainDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    void insert(DyMscMgwRetain record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    void insertSelective(DyMscMgwRetain record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    DyMscMgwRetain selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMgwRetain record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    int updateByPrimaryKey(DyMscMgwRetain record);
    
    List<DyMscMgwRetain> filter(String mscid,String startDate, String endDate);
}