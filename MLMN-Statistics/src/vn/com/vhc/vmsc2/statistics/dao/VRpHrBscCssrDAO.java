package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscCssr;

public interface VRpHrBscCssrDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR
     *
     * @ibatorgenerated Tue Apr 16 09:32:23 ICT 2013
     */
    void insert(VRpHrBscCssr record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR
     *
     * @ibatorgenerated Tue Apr 16 09:32:23 ICT 2013
     */
    void insertSelective(VRpHrBscCssr record);
    
    List<VRpHrBscCssr> getListCssr(String bscid,String dept);
    List<VRpHrBscCssr> filter(String bscid);
}