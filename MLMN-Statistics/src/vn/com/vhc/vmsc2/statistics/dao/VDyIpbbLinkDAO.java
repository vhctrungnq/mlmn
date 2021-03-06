package vn.com.vhc.vmsc2.statistics.dao;


import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.IPBBLinkTruyenDan;
import vn.com.vhc.vmsc2.statistics.domain.VDyIpbbLink;

public interface VDyIpbbLinkDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_IPBB_LINK
     *
     * @ibatorgenerated Wed Nov 21 09:48:55 ICT 2012
     */
    void insert(VDyIpbbLink record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_IPBB_LINK
     *
     * @ibatorgenerated Wed Nov 21 09:48:55 ICT 2012
     */
    void insertSelective(VDyIpbbLink record);
    
    List<VDyIpbbLink> filter(String startDate, String endDate, String link, String direction);
    List<VDyIpbbLink> filterBadLink(String startDate, String endDate, String link, String direction);
    
    /**
     * @author galaxy
     * @creatdate 05-11-2015
     * @decription Bao cao BW link truyen dan tuan, thang, quy, nam
     * */
    List<IPBBLinkTruyenDan> filterWeek(int startYear, int endYear, int startWeek, int endWeek, String link, String direction);
    List<IPBBLinkTruyenDan> filterMonth(int startYear, int endYear, int startMonth, int endMonth, String link, String direction);
    List<IPBBLinkTruyenDan> filterQuarter(int startYear, int endYear, int startQuarter, int endQuarter, String link, String direction);
    List<IPBBLinkTruyenDan> filterYear(int startYear, int endYear, String link, String direction);
    List<VDyIpbbLink> filterOption(String startTime, String endTime, String link, String direction);
}