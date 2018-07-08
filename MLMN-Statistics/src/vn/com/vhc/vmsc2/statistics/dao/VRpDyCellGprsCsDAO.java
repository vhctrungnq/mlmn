package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionCellGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsCs;

public interface VRpDyCellGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 11 16:06:33 ICT 2010
     */
    void insert(VRpDyCellGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 11 16:06:33 ICT 2010
     */
    void insertSelective(VRpDyCellGprsCs record);
    List<VRpDyCellGprsCs> filter(String startDate, String endDate, String cellid, String province, String bscid, String region, int startRecord, int endRecord, String column, String order);
    List<VRpDyCellGprsCs> filter(String startDate, String endDate, String bscid,String cellid);

	Integer countRow(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCellGprsCs> filter(String startDate, String endDate, String cellid, String province, String bscid, String region);
	
	/**
	 * @author galaxy
	 * @createDate 13/10/2015 16:21
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionCellGprsCs> cellGprsCsOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String cellid , String province, String region, 
			int startRecord, int endRecord, String column, int order);
	
	OptionCellGprsCs countCellGprsCsOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String cellid , String province, String region);
}