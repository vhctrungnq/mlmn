package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Vdycell3gQos;

public interface Vdycell3gQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:40:23 ICT 2012
     */
    void insert(Vdycell3gQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:40:23 ICT 2012
     */
    void insertSelective(Vdycell3gQos record);
    /*Unavailable cells*/
    List<Vdycell3gQos> filter(String startDate, String endDate, String bscid, String cellid);
    /*No HSDPA DATA 3G CELLS*/
    List<Vdycell3gQos> filter1(String startDate, String endDate, String bscid, String cellid);
    /*Low CSSR RNC*/
    List<Vdycell3gQos> filter2(String startDate, String endDate, String bscid, String cellid);
    /*High HSDPA DATA 3G CElLS*/
    List<Vdycell3gQos> filter3(String startDate, String endDate, String bscid, String cellid);
    
}