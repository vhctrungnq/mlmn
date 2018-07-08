package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.HrBscPsrQos;

public interface HrBscPsrQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    void insert(HrBscPsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    void insertSelective(HrBscPsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    HrBscPsrQos selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    int updateByPrimaryKeySelective(HrBscPsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_PSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 13:49:50 ICT 2010
     */
    int updateByPrimaryKey(HrBscPsrQos record);
}