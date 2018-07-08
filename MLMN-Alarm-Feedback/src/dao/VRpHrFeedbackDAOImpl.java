package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.VRpHrFeedback;

public class VRpHrFeedbackDAOImpl extends SqlMapClientDaoSupport implements VRpHrFeedbackDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_FEEDBACK
     *
     * @ibatorgenerated Thu Oct 06 14:57:03 ICT 2016
     */
    public VRpHrFeedbackDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_FEEDBACK
     *
     * @ibatorgenerated Thu Oct 06 14:57:03 ICT 2016
     */
    public void insert(VRpHrFeedback record) {
        getSqlMapClientTemplate().insert("V_RP_HR_FEEDBACK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_FEEDBACK
     *
     * @ibatorgenerated Thu Oct 06 14:57:03 ICT 2016
     */
    public void insertSelective(VRpHrFeedback record) {
        getSqlMapClientTemplate().insert("V_RP_HR_FEEDBACK.ibatorgenerated_insertSelective", record);
    }

	@Override
	public List<VRpHrFeedback> getDataForChart(String date, String province) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_date", date);
		map.put("p_province", province);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_FEEDBACK.getDataForChart", map);
	}
}