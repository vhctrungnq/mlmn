package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkLinksetReport;
import vn.com.vhc.vmsc2.statistics.web.filter.WkLinksetReportFilter;

public interface WkLinksetReportDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    int deleteByPrimaryKey(String fromNode, String toNode, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    void insert(WkLinksetReport record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    void insertSelective(WkLinksetReport record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    WkLinksetReport selectByPrimaryKey(String fromNode, String toNode, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    int updateByPrimaryKeySelective(WkLinksetReport record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:50 ICT 2010
     */
    int updateByPrimaryKey(WkLinksetReport record);

	List<WkLinksetReport> filter(WkLinksetReportFilter filter);
}