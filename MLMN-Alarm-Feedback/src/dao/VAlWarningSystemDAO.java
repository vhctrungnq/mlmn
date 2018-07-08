package dao;

import java.util.List;

import vo.VAlWarningSystem;

public interface VAlWarningSystemDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_WARNING_SYSTEM
     *
     * @ibatorgenerated Fri Nov 02 17:06:16 ICT 2012
     */
    void insert(VAlWarningSystem record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_WARNING_SYSTEM
     *
     * @ibatorgenerated Fri Nov 02 17:06:16 ICT 2012
     */
    void insertSelective(VAlWarningSystem record);
    
    public List<String> getSystemByTypeWarning(String systemName) ;

	List<String> checkSystemExits(String system,String systemType);
}