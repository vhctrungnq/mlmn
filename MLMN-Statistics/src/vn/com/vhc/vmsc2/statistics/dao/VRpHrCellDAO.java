package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrCell;

public interface VRpHrCellDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL
	 * 
	 * @ibatorgenerated Fri Nov 12 10:47:32 ICT 2010
	 */
	void insert(VRpHrCell record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL
	 * 
	 * @ibatorgenerated Fri Nov 12 10:47:32 ICT 2010
	 */
	void insertSelective(VRpHrCell record);

	List<VRpHrCell> filter(Date day, Integer hour, String cellid, String province, String bscid);

	List<VRpHrCell> filter(String startHour, String endHour, Date day, String bscid, String cellid);
	List<VRpHrCell> filter1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String cellid);
	List<VRpHrCell> filter2(String startHour, Date startDate, String endHour, String bscid, String cellid);
    List<VRpHrCell> filterQualityNetwork(String module, String bscid, String cellid,String startDate, String startHour, String endHour);

}