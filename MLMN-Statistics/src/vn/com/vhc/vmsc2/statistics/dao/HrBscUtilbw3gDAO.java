package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrBscUtilbw3g;

public interface HrBscUtilbw3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    void insert(HrBscUtilbw3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    void insertSelective(HrBscUtilbw3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    HrBscUtilbw3g selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    int updateByPrimaryKeySelective(HrBscUtilbw3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    int updateByPrimaryKey(HrBscUtilbw3g record);
    
    List<HrBscUtilbw3g> dataList(String sdate, String shour, String edate, String ehour, String bsc, int order, String column);

}