package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Vhrcell3gQos;

public interface Vhrcell3gQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:10 ICT 2012
     */
    void insert(Vhrcell3gQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:39:10 ICT 2012
     */
    void insertSelective(Vhrcell3gQos record);
    /*Unavailable cells*/
    List<Vhrcell3gQos> filter(String startHour, Date startDate, String endHour, Date endDate, String bscid,String cellid );
	List<Vhrcell3gQos> filter2(String startHour, Date startDate, String endHour, String bscid, String cellid);
	/*NO HSDPA DATA CELLS*/
    List<Vhrcell3gQos> filter1(String startHour, Date startDate, String endHour, Date endDate, String bscid,String cellid );
	List<Vhrcell3gQos> filter3(String startHour, Date startDate, String endHour, String bscid, String cellid);
	/*Low CSSR RNC*/
	List<Vhrcell3gQos> filter4(String startHour, Date startDate, String endHour, Date endDate, String bscid,String cellid );
	List<Vhrcell3gQos> filter5(String startHour, Date startDate, String endHour, String bscid, String cellid);
	/*High HSDPA DATA 3G CELLS*/
	List<Vhrcell3gQos> filter6(String startHour, Date startDate, String endHour, Date endDate, String bscid,String cellid );
	List<Vhrcell3gQos> filter7(String startHour, Date startDate, String endHour, String bscid, String cellid);
}