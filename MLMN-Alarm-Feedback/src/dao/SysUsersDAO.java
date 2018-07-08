package dao;

import java.util.List;

import vo.SYS_PARAMETER;
import vo.SysUsers;

public interface SysUsersDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    void insert(SysUsers record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    void insertSelective(SysUsers record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    SysUsers selectByPrimaryKey(String username);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    int updateByPrimaryKeySelective(SysUsers record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    int updateByPrimaryKey(SysUsers record);
    List<SysUsers> getUsersFilter(String maPhong, String username, String email, String phone, String isEnable, 
			String rolesAddUsers, String fullName, String column, String order);
    List<SysUsers> selectSysUsers();
    int updateIsEnable(SysUsers record);
    SysUsers selectByID(String id);
    List<SysUsers> getUserByMaPhong(String maPhong);
    List<SysUsers> getUsersByMaPhongDontId(String maPhong, Integer id);
    int selectByUsernamePassword(String username, String password);
    int updatePasswordForUsername(String username, String password);

	List<SysUsers> selectAllSysUsers();

	SysUsers selectSysUsersByUsername(String username);

	SysUsers selectSysUsersByfullName(String user);

	List<SYS_PARAMETER> titleSysUsers();

	List<SYS_PARAMETER> getQuyenTaoNDList();

	List<SYS_PARAMETER> getTrangThaiList();

	List<SYS_PARAMETER> titleDoiMatKhau();

	List<SysUsers> selectUsersOfAlarm();

	List<SysUsers> checkUser(String userN);

	List<SysUsers> getUserByUsername(String username);

	boolean getCountUserOfModule(String username, String system);

	List<SysUsers> getUserByGroupSMS(String group);

	/*int updateRolesForUsername(String username, String receivingSms,
			String receivingEmail, String rolesAddUsers, String modifiedBy);*/

	int updateSysUserArea(String id, String isEnable, String alarmKpi);

	int smsAddIsdn(String phone, String username);

	int copyRoleToUsernameNew(String username, String createdBy, String usernameNew,
			String passwordNew, String emailNew);

	int deleteRoleByUsername(String username);

	List<SysUsers> getCheckSysUsersByEmail(String email, String id);
	// lay danh sach lien he ten/sdt
	List<String> getContactUser(String depCode);

	List<SysUsers> checkEmailOfShift(String email);

	SysUsers getUserByPhone(String phoneNumber);

/*	List<SysUsers> getUserAllByMaPhong(String maPhong);*/

	int updateRolesForUsername(SysUsers record);

	List<SysUsers> getUserAllByMaPhong(String dept, String team);
	List<SysUsers> checkRole(String userN);
	//lay user co chuc vu ngang bang hoac be hon nguoi giao viec
	List<SysUsers> getUserForWork(String deptCode, String diliver);

	//lay thong tin Use kèm theo một số thông tin như khu vực use trực thuộc. AnhCTV:16/11/2015
	SysUsers selectByUsename(String username);
	// check=1: user thuoc phong ky thuat. Lay thong tin dai vt tu du lieu import, nguoc lai: lay thong tin dai vt la phong ban cua user truc thuoc. AnhCTV:13/11/2017
	int getUserRoleQLDN(String username);
// lay danh sach user thuoc phong ban va quan ly quan huyen AnhCTV. 14/03/2018
	List<SysUsers> getUserByDeptDistrict(String deptProcess, String district);
}