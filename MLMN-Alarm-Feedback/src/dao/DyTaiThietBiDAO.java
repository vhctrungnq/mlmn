package dao;

import java.util.List;

import vo.DyTaiThietBi;

public interface DyTaiThietBiDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_TAI_THIET_BI
     *
     * @ibatorgenerated Tue Mar 15 14:53:38 ICT 2016
     */
    void insert(DyTaiThietBi record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_TAI_THIET_BI
     *
     * @ibatorgenerated Tue Mar 15 14:53:38 ICT 2016
     */
    void insertSelective(DyTaiThietBi record);

	List<DyTaiThietBi> filterByDay(String filterColumn, String date, String region, String min, String max);

	List<DyTaiThietBi> getDay(String startDate, String endDate);

	List<DyTaiThietBi> getNe(String column, String region, String min, String max);

	List<DyTaiThietBi> get30NgayGanNhat(String column, String min, String max, String ne, String date);
}