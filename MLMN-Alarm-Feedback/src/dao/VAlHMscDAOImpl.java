package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VAlHMsc;

public class VAlHMscDAOImpl extends SqlMapClientDaoSupport implements VAlHMscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_MSC
     *
     * @ibatorgenerated Wed Feb 27 13:53:58 ICT 2013
     */
    public VAlHMscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_MSC
     *
     * @ibatorgenerated Wed Feb 27 13:53:58 ICT 2013
     */
    public void insert(VAlHMsc record) {
        getSqlMapClientTemplate().insert("V_AL_H_MSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_MSC
     *
     * @ibatorgenerated Wed Feb 27 13:53:58 ICT 2013
     */
    public void insertSelective(VAlHMsc record) {
        getSqlMapClientTemplate().insert("V_AL_H_MSC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<String> getMscByVendor(String vendor) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_VENDOR", vendor);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_H_MSC.getMscByVendor", parms);
	}
}