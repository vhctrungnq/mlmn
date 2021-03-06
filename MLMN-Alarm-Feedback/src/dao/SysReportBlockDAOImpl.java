package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.SYS_PARAMETER;
import vo.SysReportBlock;

public class SysReportBlockDAOImpl extends SqlMapClientDaoSupport implements SysReportBlockDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public SysReportBlockDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public int deleteByPrimaryKey(Integer id) {
        SysReportBlock key = new SysReportBlock();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("SYS_REPORT_BLOCK.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void insert(SysReportBlock record) {
        getSqlMapClientTemplate().insert("SYS_REPORT_BLOCK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void insertSelective(SysReportBlock record) {
        getSqlMapClientTemplate().insert("SYS_REPORT_BLOCK.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public SysReportBlock selectByPrimaryKey(Integer id) {
        SysReportBlock key = new SysReportBlock();
        key.setId(id);
        SysReportBlock record = (SysReportBlock) getSqlMapClientTemplate().queryForObject("SYS_REPORT_BLOCK.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public int updateByPrimaryKeySelective(SysReportBlock record) {
        int rows = getSqlMapClientTemplate().update("SYS_REPORT_BLOCK.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public int updateByPrimaryKey(SysReportBlock record) {
        int rows = getSqlMapClientTemplate().update("SYS_REPORT_BLOCK.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleRpFeedbackMLMN(String function) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_FUNCTION", function);  
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("SYS_REPORT_BLOCK.titleRpFeedbackMLMN", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysReportBlock> getBlockListReport(String reportType,
			String function) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_REPORT_TYPE", reportType);  
		map.put("P_FUNCTION", function);  
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("SYS_REPORT_BLOCK.getBlockListReport", map);
	}
}