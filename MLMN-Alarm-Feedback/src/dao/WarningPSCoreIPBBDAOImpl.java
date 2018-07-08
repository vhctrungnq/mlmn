package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.WarningMore;
import vo.WarningPSCoreIPBB;

public class WarningPSCoreIPBBDAOImpl extends SqlMapClientDaoSupport implements WarningPSCoreIPBBDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public WarningPSCoreIPBBDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        WarningPSCoreIPBB key = new WarningPSCoreIPBB();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("WARNING_PSCORE_IPBB.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public void insert(WarningPSCoreIPBB record) {
        getSqlMapClientTemplate().insert("WARNING_PSCORE_IPBB.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public void insertSelective(WarningPSCoreIPBB record) {
        getSqlMapClientTemplate().insert("WARNING_PSCORE_IPBB.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public WarningPSCoreIPBB selectByPrimaryKey(Integer id) {
        WarningPSCoreIPBB key = new WarningPSCoreIPBB();
        key.setId(id);
        WarningPSCoreIPBB record = (WarningPSCoreIPBB) getSqlMapClientTemplate().queryForObject("WARNING_PSCORE_IPBB.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public int updateByPrimaryKeySelective(WarningPSCoreIPBB record) {
        int rows = getSqlMapClientTemplate().update("WARNING_PSCORE_IPBB.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WARNING_PSCORE_IPBB
     *
     * @ibatorgenerated Fri Oct 19 09:44:14 ICT 2012
     */
    public int updateByPrimaryKey(WarningPSCoreIPBB record) {
        int rows = getSqlMapClientTemplate().update("WARNING_PSCORE_IPBB.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<WarningMore> getWarningList(String logType,
			String warningName, String mscid, String methodTreatment,
			String userExcute, String start_Time, String end_Time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warningType", logType);
		map.put("warningName", warningName);
		map.put("mscid", mscid);
		map.put("methodTreatment", methodTreatment);
		map.put("userExcute", userExcute);
		map.put("start_Time", start_Time);
		map.put("end_Time", end_Time);
		return getSqlMapClientTemplate().queryForList("WARNING_PSCORE_IPBB.getWarningList", map);
	}
}