package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.SummaryLog3g;
import vn.com.vhc.vmsc2.statistics.web.filter.SummaryLogFilter;

public interface SummaryLog3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table S_SUMMARY_LOGS_3G
     *
     * @ibatorgenerated Thu Sep 13 09:59:29 ICT 2012
     */
    void insert(SummaryLog3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table S_SUMMARY_LOGS_3G
     *
     * @ibatorgenerated Thu Sep 13 09:59:29 ICT 2012
     */
    void insertSelective(SummaryLog3g record);
    
    List<SummaryLog3g> filter(SummaryLogFilter filter);

	Integer countRow(SummaryLogFilter filter);
}