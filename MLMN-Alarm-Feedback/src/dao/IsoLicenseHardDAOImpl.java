package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.IsoLicenseHard;

public class IsoLicenseHardDAOImpl extends SqlMapClientDaoSupport implements IsoLicenseHardDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public IsoLicenseHardDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        IsoLicenseHard key = new IsoLicenseHard();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ISO_LICENSE_HARD.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public void insert(IsoLicenseHard record) {
        getSqlMapClientTemplate().insert("ISO_LICENSE_HARD.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public void insertSelective(IsoLicenseHard record) {
        getSqlMapClientTemplate().insert("ISO_LICENSE_HARD.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public IsoLicenseHard selectByPrimaryKey(Integer id) {
        IsoLicenseHard key = new IsoLicenseHard();
        key.setId(id);
        IsoLicenseHard record = (IsoLicenseHard) getSqlMapClientTemplate().queryForObject("ISO_LICENSE_HARD.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public int updateByPrimaryKeySelective(IsoLicenseHard record) {
        int rows = getSqlMapClientTemplate().update("ISO_LICENSE_HARD.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_LICENSE_HARD
     *
     * @ibatorgenerated Tue Sep 24 21:20:31 ICT 2013
     */
    public int updateByPrimaryKey(IsoLicenseHard record) {
        int rows = getSqlMapClientTemplate().update("ISO_LICENSE_HARD.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoLicenseHard> getLicenseHardByNeType(String startDate, String endDate, String neType) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_NE_TYPE", neType);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_LICENSE_HARD.getLicenseHardByNeType", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoLicenseHard> getLicenseHardBsc(String startDate, String endDate) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_LICENSE_HARD.getLicenseHardBsc", map);
    }
}