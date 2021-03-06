package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.ConvertLog;
import vn.com.vhc.vmsc2.statistics.web.filter.FileControlFilter;

public class ConvertLogDAOImpl extends SqlMapClientDaoSupport implements ConvertLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public ConvertLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public void insert(ConvertLog record) {
        getSqlMapClientTemplate().insert("I_FILE_CONVERT_LOGS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table I_FILE_CONVERT_LOGS
     *
     * @ibatorgenerated Fri Sep 10 15:51:14 ICT 2010
     */
    public void insertSelective(ConvertLog record) {
        getSqlMapClientTemplate().insert("I_FILE_CONVERT_LOGS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<ConvertLog> filter(FileControlFilter filter) {
		return getSqlMapClientTemplate().queryForList("I_FILE_CONVERT_LOGS.filter", filter);
	}

	public Integer countRow(FileControlFilter filter) {
		return (Integer) getSqlMapClientTemplate().queryForObject("I_FILE_CONVERT_LOGS.countRow", filter);
	}
}