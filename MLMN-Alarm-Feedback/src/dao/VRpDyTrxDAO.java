package dao;

import java.util.List;

import vo.VRpDyTrx;

public interface VRpDyTrxDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_TRX
     *
     * @ibatorgenerated Tue Nov 12 09:25:19 ICT 2013
     */
    void insert(VRpDyTrx record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_TRX
     *
     * @ibatorgenerated Tue Nov 12 09:25:19 ICT 2013
     */
    void insertSelective(VRpDyTrx record);

	List<VRpDyTrx> getSiteCellList();
}