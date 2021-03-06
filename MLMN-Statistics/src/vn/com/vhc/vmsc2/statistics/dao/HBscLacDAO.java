package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HBscLac;

public interface HBscLacDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    int deleteByPrimaryKey(String bscid, Integer lac);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    void insert(HBscLac record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    void insertSelective(HBscLac record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    HBscLac selectByPrimaryKey(String bscid, Integer lac);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    int updateByPrimaryKeySelective(HBscLac record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    int updateByPrimaryKey(HBscLac record);
    List<HBscLac> filter(HBscLac filter);
}