package dao;

import java.util.List;

import vo.VRpDyAccNw3G;

public interface VRpDyAccNw3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_3G
     *
     * @ibatorgenerated Thu Nov 01 11:00:43 ICT 2012
     */
    void insert(VRpDyAccNw3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_3G
     *
     * @ibatorgenerated Thu Nov 01 11:00:43 ICT 2012
     */
    void insertSelective(VRpDyAccNw3G record);
    
    List<VRpDyAccNw3G> filter(String day, String region);
    List<VRpDyAccNw3G> filerOfHomePage(String tableName, String day, String region);
}