package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.SummaryLogCore;
import vn.com.vhc.vmsc2.statistics.web.filter.SummaryLogFilter;

public interface SummaryLogCoreDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table S_SUMMARY_LOGS_CORE
     *
     * @ibatorgenerated Thu Sep 13 10:06:36 ICT 2012
     */
    void insert(SummaryLogCore record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table S_SUMMARY_LOGS_CORE
     *
     * @ibatorgenerated Thu Sep 13 10:06:36 ICT 2012
     */
    void insertSelective(SummaryLogCore record);
    
    List<SummaryLogCore> filter(SummaryLogFilter filter);

	Integer countRow(SummaryLogFilter filter);
}