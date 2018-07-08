package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyMscQos;

public interface DyMscQosDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	int deleteByPrimaryKey(Date day, String mscid);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	void insert(DyMscQos record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	void insertSelective(DyMscQos record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	DyMscQos selectByPrimaryKey(Date day, String mscid);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	int updateByPrimaryKeySelective(DyMscQos record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table DY_MSC_QOS
	 * 
	 * @ibatorgenerated Fri Oct 08 11:36:16 ICT 2010
	 */
	int updateByPrimaryKey(DyMscQos record);

	List<DyMscQos> filter(String mscid, String startDate, String endDate);

	List<DyMscQos> filter(Date day, String mscid);
}