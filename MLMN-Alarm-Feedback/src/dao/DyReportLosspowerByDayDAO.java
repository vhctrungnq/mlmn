package dao;

import java.util.List;

import vo.DyReportLosspowerByDay;
import vo.SYS_PARAMETER;

public interface DyReportLosspowerByDayDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    void insert(DyReportLosspowerByDay record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    void insertSelective(DyReportLosspowerByDay record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    DyReportLosspowerByDay selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    int updateByPrimaryKeySelective(DyReportLosspowerByDay record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REPORT_LOSSPOWER_BY_DAY
     *
     * @ibatorgenerated Sat Dec 01 10:55:01 ICT 2012
     */
    int updateByPrimaryKey(DyReportLosspowerByDay record);

	List<DyReportLosspowerByDay> createReportWithDay(String startTime,
			String endTime);

	List<SYS_PARAMETER> titleReportLossPowerByDay();
}