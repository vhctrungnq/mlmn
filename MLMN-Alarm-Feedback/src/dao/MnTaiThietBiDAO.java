package dao;

import java.util.List;

import vo.MnTaiThietBi;

public interface MnTaiThietBiDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_TAI_THIET_BI
     *
     * @ibatorgenerated Tue Mar 15 14:55:06 ICT 2016
     */
    void insert(MnTaiThietBi record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_TAI_THIET_BI
     *
     * @ibatorgenerated Tue Mar 15 14:55:06 ICT 2016
     */
    void insertSelective(MnTaiThietBi record);

	List<MnTaiThietBi> filterByMonth(String filterColumn, Integer month, String year, String region, String min, String max);
			
}