package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Role;
import vn.com.vhc.vmsc2.statistics.domain.UserRole;


public interface UserRoleDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_USER_ROLE
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    int deleteByPrimaryKey(String roleId, Integer userId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_USER_ROLE
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    void insert(UserRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_USER_ROLE
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    void insertSelective(UserRole record);
    
    List<Role> getByUser(Integer userId);
	void deleteByUser(Integer userId);
}