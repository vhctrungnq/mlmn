package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkBscCore;

public interface WkBscCoreDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insert(WkBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insertSelective(WkBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    WkBscCore selectByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKeySelective(WkBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKey(WkBscCore record);
    List<WkBscCore> filterWeekAndBsc(Integer week,Integer year, String bscid);
}