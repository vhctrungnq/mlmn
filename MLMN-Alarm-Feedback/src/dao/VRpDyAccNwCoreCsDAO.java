package dao;

import java.util.List;

import vo.VRpDyAccNwCoreCs;

public interface VRpDyAccNwCoreCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_CORE_CS
     *
     * @ibatorgenerated Thu Nov 01 11:07:38 ICT 2012
     */
    void insert(VRpDyAccNwCoreCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ACC_NW_CORE_CS
     *
     * @ibatorgenerated Thu Nov 01 11:07:38 ICT 2012
     */
    void insertSelective(VRpDyAccNwCoreCs record);
    
    List<VRpDyAccNwCoreCs> filter(String day);
}