package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionCell3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell3G;


public interface VRpDyCell3GDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_CELL_3G
	 * 
	 * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
	 */
	void insert(VRpDyCell3G record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_CELL_3G
	 * 
	 * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
	 */
	void insertSelective(VRpDyCell3G record);

	List<VRpDyCell3G> filter(String startDate, String endDate, String cellid, String province, String bscid, String region, int startRecord, int endRecord,
			String column, String order);

	Integer countRow(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCell3G> filter(String startDate, String endDate, String bscid, String cellid);

	List<VRpDyCell3G> filter(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCell3G> filterArrayExports(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCell3G> filterArray(String startDate, String endDate, String cellid, String province, String bscid, String region, int startRecord,
			int endRecord, String column, String order);

	Integer countRowArray(String startDate, String endDate, String cellid, String province, String bscid, String region);
	 /*Low CSSR RNC*/
    List<VRpDyCell3G> filterLowCssr(String startDate, String endDate, String bscid, String cellid);
    /*High HSDPA DATA 3G CElLS*/
    List<VRpDyCell3G> filterHsdpa(String startDate, String endDate, String bscid, String cellid);
    /*High HSDPA DATA 3G CElLS*/
    List<VRpDyCell3G> filterNoHsdpa(String startDate, String endDate, String bscid, String cellid);

	/**
	 * @author TrungNQ
	 * @createDate 17/11/2015 16:21
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionCell3g> Cell3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String bscid, String cellid, 
			int startRecord, int endRecord, String column, int order);
	
	OptionCell3g countCell3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String bscid, String cellid);
	
	 List<String> getAllBsc();
}