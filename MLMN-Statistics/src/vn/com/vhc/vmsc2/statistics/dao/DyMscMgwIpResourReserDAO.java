package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwIpResourReser;

public interface DyMscMgwIpResourReserDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    void insert(DyMscMgwIpResourReser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    void insertSelective(DyMscMgwIpResourReser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    DyMscMgwIpResourReser selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMgwIpResourReser record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_RESOUR_RESER
     *
     * @ibatorgenerated Mon Nov 05 14:29:47 ICT 2012
     */
    int updateByPrimaryKey(DyMscMgwIpResourReser record);
    
    List<DyMscMgwIpResourReser> filter(String mscid,String startDate, String endDate);
}