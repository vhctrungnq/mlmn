package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionCell;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell;

public interface VRpDyCellDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL
     *
     * @ibatorgenerated Thu Jul 26 16:30:13 ICT 2012
     */
    void insert(VRpDyCell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL
     *
     * @ibatorgenerated Thu Jul 26 16:30:13 ICT 2012
     */
    void insertSelective(VRpDyCell record);
    List<VRpDyCell> filter(Date day, String cellid, String province, String bscid);
    List<VRpDyCell> filter( String startDate, String endDate, String bscid, String cellid);
    List<VRpDyCell> filtertDrpr( String startDate, String endDate, String bscid, String cellid);
    List<VRpDyCell> filtersDrpr( String startDate, String endDate, String bscid, String cellid);
    List<VRpDyCell> filtertBlkr( String startDate, String endDate, String bscid, String cellid);
    List<VRpDyCell> filtersBlkr( String startDate, String endDate, String bscid, String cellid);  
    List<VRpDyCell> filterdyloss( String startDate, String endDate, String bscid, String cellid);
    List<VRpDyCell> filterCenter2(String startDate,String endDate,String bscid, String cellid);
    List<VRpDyCell> filterCenter(String startDate,String endDate,String bscid, String cellid);
  
	/**
	 * @author TrungNQ
	 * @createDate 23/10/2015
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionCell> cellOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String cellid , String province, String region, 
			int startRecord, int endRecord, String column, int order);
	
	OptionCell countCellOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String cellid , String province, String region);
}