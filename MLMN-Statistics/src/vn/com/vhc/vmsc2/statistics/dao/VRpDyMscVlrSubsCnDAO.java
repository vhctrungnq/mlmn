package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscVlrSubsCn;
import vn.com.vhc.vmsc2.statistics.web.filter.MscVlrSubsFilter;

public interface VRpDyMscVlrSubsCnDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_VLRSUBS_CN
     *
     * @ibatorgenerated Fri Jan 10 14:31:12 ICT 2014
     */
    void insert(VRpDyMscVlrSubsCn record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_VLRSUBS_CN
     *
     * @ibatorgenerated Fri Jan 10 14:31:12 ICT 2014
     */
    void insertSelective(VRpDyMscVlrSubsCn record);
    
    List<VRpDyMscVlrSubsCn> getDataList(MscVlrSubsFilter filter);
}