package dao;

import java.util.List;


import vo.VRpHrCellCssr;

public interface VRpHrCellCssrDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_CELL_CSSR
     *
     * @ibatorgenerated Thu Apr 18 13:58:31 ICT 2013
     */
    void insert(VRpHrCellCssr record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_CELL_CSSR
     *
     * @ibatorgenerated Thu Apr 18 13:58:31 ICT 2013
     */
    void insertSelective(VRpHrCellCssr record);
    List<VRpHrCellCssr> getListCssrCell(String bscid);
}