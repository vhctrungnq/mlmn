package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import vn.com.vhc.vmsc2.statistics.domain.DyMtclDistrict;

public interface DyMtclDistrictDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    int deleteByPrimaryKey(Date day, String district, String province);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    void insert(DyMtclDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    void insertSelective(DyMtclDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    DyMtclDistrict selectByPrimaryKey(Date day, String district, String province);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    int updateByPrimaryKeySelective(DyMtclDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MTCL_DISTRICT
     *
     * @ibatorgenerated Sat Jul 07 16:51:05 ICT 2018
     */
    int updateByPrimaryKey(DyMtclDistrict record);
}