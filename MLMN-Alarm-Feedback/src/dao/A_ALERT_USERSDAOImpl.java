package dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.A_ALERT_USERS;

public class A_ALERT_USERSDAOImpl extends SqlMapClientDaoSupport implements A_ALERT_USERSDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public A_ALERT_USERSDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public int deleteByPrimaryKey(Integer userId) {
        A_ALERT_USERS key = new A_ALERT_USERS();
        key.setUserId(userId);
        int rows = getSqlMapClientTemplate().delete("A_ALERT_USERS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public void insert(A_ALERT_USERS record) {
        getSqlMapClientTemplate().insert("A_ALERT_USERS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public void insertSelective(A_ALERT_USERS record) {
        getSqlMapClientTemplate().insert("A_ALERT_USERS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public A_ALERT_USERS selectByPrimaryKey(Integer userId) {
        A_ALERT_USERS key = new A_ALERT_USERS();
        key.setUserId(userId);
        A_ALERT_USERS record = (A_ALERT_USERS) getSqlMapClientTemplate().queryForObject("A_ALERT_USERS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public int updateByPrimaryKeySelective(A_ALERT_USERS record) {
        int rows = getSqlMapClientTemplate().update("A_ALERT_USERS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table A_ALERT_USERS
     *
     * @ibatorgenerated Thu Aug 09 15:08:47 ICT 2012
     */
    public int updateByPrimaryKey(A_ALERT_USERS record) {
        int rows = getSqlMapClientTemplate().update("A_ALERT_USERS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<A_ALERT_USERS> getFullNameList(){
		return getSqlMapClientTemplate().queryForList("A_ALERT_USERS.fullNameList");
		}
	
	public A_ALERT_USERS getAlertUsersByUsername(String username)
	{
		 A_ALERT_USERS record = (A_ALERT_USERS) getSqlMapClientTemplate().queryForObject("A_ALERT_USERS.getAlertUsersByUsername", username);
		 return record;
	}
	
	public A_ALERT_USERS getAlertUsersByFullname(String fullname){
		 A_ALERT_USERS record = (A_ALERT_USERS) getSqlMapClientTemplate().queryForObject("A_ALERT_USERS.getAlertUsersByFullname", fullname);
		 return record;
	}
	
	@Override
	public Integer countNguoiChuTri(String nguoiChuTri) {
		return (Integer) getSqlMapClientTemplate().queryForObject("A_ALERT_USERS.countNguoiChuTri",
				nguoiChuTri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<A_ALERT_USERS> getUserByDeparment(String deptCode) {
		 A_ALERT_USERS key = new A_ALERT_USERS();
	     key.setDepartment(deptCode);
	     return getSqlMapClientTemplate().queryForList("A_ALERT_USERS.getUserByDeparment", key);
	}
}