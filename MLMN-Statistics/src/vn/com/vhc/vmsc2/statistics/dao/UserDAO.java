package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.User;


public interface UserDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    void insert(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    void insertSelective(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    int updateByPrimaryKey(User record);

	List<User> getAll();

	User getByUsername(String currentUser);
	
	List<User> getAllByFullNameAndDepartment(String fullName, String department);
}