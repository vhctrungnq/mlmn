package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscLossRoute;

public interface DyMscLossRouteDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    void insert(DyMscLossRoute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    void insertSelective(DyMscLossRoute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    DyMscLossRoute selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscLossRoute record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_LOSSROUTE
     *
     * @ibatorgenerated Mon Oct 15 09:40:19 ICT 2012
     */
    int updateByPrimaryKey(DyMscLossRoute record);
    
    List<DyMscLossRoute> filter( String mscid,String startDate, String endDate);
}