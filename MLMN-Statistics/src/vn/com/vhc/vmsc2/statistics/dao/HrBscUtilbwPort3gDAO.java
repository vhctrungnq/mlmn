package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrBscUtilbwPort3g;

public interface HrBscUtilbwPort3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    void insert(HrBscUtilbwPort3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    void insertSelective(HrBscUtilbwPort3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    HrBscUtilbwPort3g selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    int updateByPrimaryKeySelective(HrBscUtilbwPort3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_PORT_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:53 ICT 2013
     */
    int updateByPrimaryKey(HrBscUtilbwPort3g record);
    
    List<HrBscUtilbwPort3g> dataList(String sdate, String shour, String edate, String ehour, String bsc, int order, String column);
}