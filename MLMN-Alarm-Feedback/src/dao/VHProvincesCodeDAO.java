package dao;

import java.util.List;

import vo.HProvincesCode;
import vo.VHProvincesCode;

public interface VHProvincesCodeDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_H_PROVINCES_CODE
     *
     * @ibatorgenerated Wed Oct 31 16:17:06 ICT 2012
     */
    void insert(VHProvincesCode record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_H_PROVINCES_CODE
     *
     * @ibatorgenerated Wed Oct 31 16:17:06 ICT 2012
     */
    void insertSelective(VHProvincesCode record);
    
    List<VHProvincesCode> getDepCodeList();
}