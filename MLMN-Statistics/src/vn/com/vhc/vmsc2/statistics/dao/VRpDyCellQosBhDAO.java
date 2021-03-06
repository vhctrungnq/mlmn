package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellQosBh;


public interface VRpDyCellQosBhDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_CELL_QOS_BH
	 * 
	 * @ibatorgenerated Mon Dec 27 09:08:26 ICT 2010
	 */
	void insert(VRpDyCellQosBh record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_CELL_QOS_BH
	 * 
	 * @ibatorgenerated Mon Dec 27 09:08:26 ICT 2010
	 */
	void insertSelective(VRpDyCellQosBh record);

	List<VRpDyCellQosBh> filter(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCellQosBh> filter(String startDate, String endDate, String cellid, String province, String bscid, String region, int startRecord, int endRecord,
			String column, String order);

	Integer countRow(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCellQosBh> filterArray(String startDate, String endDate, String cellid, String province, String bscid, String region, int startRecord,
			int endRecord, String column, String order);

	Integer countRowArray(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCellQosBh> filterArrayExports(String startDate, String endDate, String cellid, String province, String bscid, String region);
}