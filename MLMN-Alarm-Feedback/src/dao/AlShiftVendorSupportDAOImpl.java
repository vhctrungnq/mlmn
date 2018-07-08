package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
 
import vo.AlShiftVendorSupport;
import vo.SYS_PARAMETER;
import vo.alarm.utils.AlShiftVendorFilter;

public class AlShiftVendorSupportDAOImpl extends SqlMapClientDaoSupport implements AlShiftVendorSupportDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public AlShiftVendorSupportDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        AlShiftVendorSupport key = new AlShiftVendorSupport();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public void insert(AlShiftVendorSupport record) {
        getSqlMapClientTemplate().insert("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public void insertSelective(AlShiftVendorSupport record) {
        getSqlMapClientTemplate().insert("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public AlShiftVendorSupport selectByPrimaryKey(Integer id) {
        AlShiftVendorSupport key = new AlShiftVendorSupport();
        key.setId(id);
        AlShiftVendorSupport record = (AlShiftVendorSupport) getSqlMapClientTemplate().queryForObject("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }
    
    public AlShiftVendorSupport selectByUnikey(Date stime,Date etime, String phone, String email, String idNumber) {
        AlShiftVendorSupport key = new AlShiftVendorSupport();
        key.setStime(stime);
        key.setEtime(etime);
        key.setPhone(phone);
        key.setEmail(email);
        key.setIdNumber(idNumber);
        AlShiftVendorSupport record = (AlShiftVendorSupport) getSqlMapClientTemplate().queryForObject("AL_SHIFT_VENDOR_SUPPORT.selectByUnikey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public int updateByPrimaryKeySelective(AlShiftVendorSupport record) {
        int rows = getSqlMapClientTemplate().update("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_VENDOR_SUPPORT
     *
     * @ibatorgenerated Wed Apr 10 14:32:44 ICT 2013
     */
    public int updateByPrimaryKey(AlShiftVendorSupport record) {
        int rows = getSqlMapClientTemplate().update("AL_SHIFT_VENDOR_SUPPORT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    } 
    
    @SuppressWarnings("unchecked")
	@Override
	public List<AlShiftVendorSupport> getList(AlShiftVendorFilter filter,String column, int order){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_stime", filter.getsTime());
		map.put("p_etime", filter.geteTime());
		map.put("p_vendor", filter.getVendor());
		map.put("p_system",filter.getSystem());
		map.put("p_fullName", filter.getFullName());
		map.put("p_idNumber",filter.getIdNumber());
		map.put("p_supportType", filter.getSupportType());
		map.put("p_phone", filter.getPhone());
		map.put("p_region", filter.getRegion());
		map.put("p_COLUMN", column);
		map.put("p_ORDER", order);
		map.put("p_DATA", null);
		return getSqlMapClientTemplate().queryForList("AL_SHIFT_VENDOR_SUPPORT.getList", map);

	}
    
    @SuppressWarnings("unchecked")
    public List<AlShiftVendorSupport> getListOld(String stime, String etime, String vendor, String system, 
			String fullName, String phone, String email, String idNumber){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("stime", stime);
    	map.put("etime", etime);
    	map.put("vendor", vendor);
    	map.put("system", system);
    	map.put("fullname", fullName);
    	map.put("phone", phone);
    	map.put("email", email);
    	map.put("idNumber", idNumber);
    	
    	return getSqlMapClientTemplate().queryForList("AL_SHIFT_VENDOR_SUPPORT.getListOld", map);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeForm){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TYPE_FORM", typeForm);
		
		map.put("p_DATA", null);
		return getSqlMapClientTemplate().queryForList("AL_SHIFT_VENDOR_SUPPORT.titleForm", map);
	}
}