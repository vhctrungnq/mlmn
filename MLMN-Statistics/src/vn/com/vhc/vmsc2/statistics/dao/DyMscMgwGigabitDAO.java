package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwGigabit;

public interface DyMscMgwGigabitDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    int deleteByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    void insert(DyMscMgwGigabit record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    void insertSelective(DyMscMgwGigabit record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    DyMscMgwGigabit selectByPrimaryKey(Date day, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    int updateByPrimaryKeySelective(DyMscMgwGigabit record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_GIGABIT
     *
     * @ibatorgenerated Mon Oct 22 15:58:21 ICT 2012
     */
    int updateByPrimaryKey(DyMscMgwGigabit record);
    
    List<DyMscMgwGigabit> filter(String mscid,String startDate, String endDate);
}