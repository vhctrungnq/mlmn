package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwSccp;

public interface DyMscMgwSccpDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    void insert(DyMscMgwSccp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    void insertSelective(DyMscMgwSccp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    DyMscMgwSccp selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMgwSccp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SCCP
     *
     * @ibatorgenerated Tue Oct 23 12:01:21 ICT 2012
     */
    int updateByPrimaryKey(DyMscMgwSccp record);
    
    List<DyMscMgwSccp> filter(String mscid,String startDate, String endDate);
}