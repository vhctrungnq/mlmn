package dao;

import java.util.List;

import vo.Cabledropet4tlmgw;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Cabledropet4tlmgwFilter;

public interface Cabledropet4tlmgwDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_TLMGW
     *
     * @ibatorgenerated Tue Apr 09 13:57:58 ICT 2013
     */
	
    void insert(Cabledropet4tlmgw record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_TLMGW
     *
     * @ibatorgenerated Tue Apr 09 13:57:58 ICT 2013
     */
    void insertSelective(Cabledropet4tlmgw record);
    

    int updateByPrimaryKey(Cabledropet4tlmgw record);
    
    Cabledropet4tlmgw selectById(Integer id);
    
    Cabledropet4tlmgw selectByPrimaryKey(String system, String frSlotPort, String ddfEt4);
    
    int deleteByPrimaryKey(Integer id);
    
    List<Cabledropet4tlmgw> getList(Cabledropet4tlmgwFilter filter,String column, int order, Integer delData);
    
    List<SYS_PARAMETER> titleForm(String type_form);

	List<Cabledropet4tlmgw> getEt4MgwExist(String system, String frSlotPort,
			String id);
	
	List<String> getAllSystems(String term);
}