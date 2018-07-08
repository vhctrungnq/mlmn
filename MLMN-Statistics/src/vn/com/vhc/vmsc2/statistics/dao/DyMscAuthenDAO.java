package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscAuthen;

public interface DyMscAuthenDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    void insert(DyMscAuthen record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    void insertSelective(DyMscAuthen record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    DyMscAuthen selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscAuthen record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_AUTH
     *
     * @ibatorgenerated Fri Oct 12 14:46:01 ICT 2012
     */
    int updateByPrimaryKey(DyMscAuthen record);
    
    List<DyMscAuthen> filter(String starDate, String endDate, String mscid);
}