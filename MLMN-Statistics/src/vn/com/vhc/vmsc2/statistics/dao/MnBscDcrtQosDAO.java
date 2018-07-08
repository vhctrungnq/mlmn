package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnBscDcrtQos;

public interface MnBscDcrtQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insert(MnBscDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insertSelective(MnBscDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    MnBscDcrtQos selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKeySelective(MnBscDcrtQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKey(MnBscDcrtQos record);
}