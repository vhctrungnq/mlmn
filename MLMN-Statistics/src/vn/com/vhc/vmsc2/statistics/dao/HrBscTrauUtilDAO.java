package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrBscTrauUtil;

public interface HrBscTrauUtilDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    void insert(HrBscTrauUtil record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    void insertSelective(HrBscTrauUtil record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    HrBscTrauUtil selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    int updateByPrimaryKeySelective(HrBscTrauUtil record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    int updateByPrimaryKey(HrBscTrauUtil record);

	List<HrBscTrauUtil> getHrBscTrauUtilFilter(String bscid, String startDate, String endDate, String shour, String ehour, String column, String order);
}