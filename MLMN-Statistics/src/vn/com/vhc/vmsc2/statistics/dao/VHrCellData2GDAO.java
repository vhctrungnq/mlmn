package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VHrCellData2G;

public interface VHrCellData2GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:55:43 ICT 2012
     */
    void insert(VHrCellData2G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:55:43 ICT 2012
     */
    void insertSelective(VHrCellData2G record);
    
    List<VHrCellData2G> filter(String startHour, Date startDate, String endHour, Date endDate, String bscid, String cellid);
	List<VHrCellData2G> filter2(String startHour, Date startDate, String endHour, String bscid, String cellid);
	List<VHrCellData2G> filterTF(String startHour, Date startDate, String endHour, Date endDate, String bscid, String cellid);
	List<VHrCellData2G> filterTF2(String startHour, Date startDate, String endHour, String bscid, String cellid);
}