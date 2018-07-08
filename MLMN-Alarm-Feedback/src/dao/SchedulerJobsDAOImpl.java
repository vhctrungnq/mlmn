package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.SYS_PARAMETER;
import vo.SchedulerJobs;

public class SchedulerJobsDAOImpl extends SqlMapClientDaoSupport implements SchedulerJobsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_SCHEDULER_JOBS
     *
     * @ibatorgenerated Mon Oct 01 11:00:37 ICT 2012
     */
    public SchedulerJobsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_SCHEDULER_JOBS
     *
     * @ibatorgenerated Mon Oct 01 11:00:37 ICT 2012
     */
    public void insert(SchedulerJobs record) {
        getSqlMapClientTemplate().insert("DBA_SCHEDULER_JOBS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_SCHEDULER_JOBS
     *
     * @ibatorgenerated Mon Oct 01 11:00:37 ICT 2012
     */
    public void insertSelective(SchedulerJobs record) {
        getSqlMapClientTemplate().insert("DBA_SCHEDULER_JOBS.ibatorgenerated_insertSelective", record);
    }
    
   /* @SuppressWarnings("unchecked")
	public List<SchedulerJobs> filter(String jobName) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("jobName", jobName);
		return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.filter", map);
	}
    @SuppressWarnings("unchecked")
	public List<SchedulerJobs> getAll() {
    	return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.getAll");
	}*/
    @SuppressWarnings("unchecked")
	@Override
	public List<SchedulerJobs> getJobName() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.getJobName", map);
	
	}

	@Override
	public Integer countRow(String jobName) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_JOB_NAME", jobName);
    	parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("DBA_SCHEDULER_JOBS.countRow", parms);
    	Integer record = (Integer) parms.get("P_DATA");
    	return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerJobs> filter(String jobName, int startRecord,
			int endRecord, String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_JOB_NAME", jobName);
		map.put("P_START_RECORD", startRecord);
		map.put("P_END_RECORD", endRecord);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.filter", map);
	
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleFormDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.titleFormDetail", map);

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleFormList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("DBA_SCHEDULER_JOBS.titleFormList", map);

	}
}