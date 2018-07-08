package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyBscUtilce3g;

public interface DyBscUtilce3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    int deleteByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    void insert(DyBscUtilce3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    void insertSelective(DyBscUtilce3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    DyBscUtilce3g selectByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    int updateByPrimaryKeySelective(DyBscUtilce3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_UTILCE_3G
     *
     * @ibatorgenerated Mon Jul 22 14:58:23 ICT 2013
     */
    int updateByPrimaryKey(DyBscUtilce3g record);
    
    List<DyBscUtilce3g> dataList(String sdate,String edate,String bscid, int order,String column);
}