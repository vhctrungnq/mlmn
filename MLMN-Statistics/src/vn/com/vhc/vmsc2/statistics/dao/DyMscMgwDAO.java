package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgw;

public interface DyMscMgwDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    void insert(DyMscMgw record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    void insertSelective(DyMscMgw record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    DyMscMgw selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMgw record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW
     *
     * @ibatorgenerated Mon Oct 15 17:19:09 ICT 2012
     */
    int updateByPrimaryKey(DyMscMgw record);
    
    List<DyMscMgw> filter(String mscid,String startDate, String endDate);
}