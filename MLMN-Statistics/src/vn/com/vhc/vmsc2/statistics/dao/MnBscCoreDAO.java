package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.MnBscCore;

public interface MnBscCoreDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insert(MnBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insertSelective(MnBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    MnBscCore selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKeySelective(MnBscCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKey(MnBscCore record);
    List<MnBscCore> filterMonthAndBsc(Integer month,Integer year, String bscid);
}