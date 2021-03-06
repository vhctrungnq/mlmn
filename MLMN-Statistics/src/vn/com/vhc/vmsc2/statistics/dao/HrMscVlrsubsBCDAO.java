package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HrMscVlrsubsBC;

public interface HrMscVlrsubsBCDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    int deleteByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    void insert(HrMscVlrsubsBC record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    void insertSelective(HrMscVlrsubsBC record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    HrMscVlrsubsBC selectByPrimaryKey(Date day, Integer hour, String mscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    int updateByPrimaryKeySelective(HrMscVlrsubsBC record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    int updateByPrimaryKey(HrMscVlrsubsBC record);
    List<HrMscVlrsubsBC> filter(String startHour, Date startDate, String endHour, Date endDate, String mscid);
   	List<HrMscVlrsubsBC> filter2(String startHour, Date startDate, String endHour, String mscid);

}