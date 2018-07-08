package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.RawFile;
import vn.com.vhc.vmsc2.statistics.web.filter.RawFileFilter;

public class RawFileDAOImpl extends SqlMapClientDaoSupport implements RawFileDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public RawFileDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public int deleteByPrimaryKey(Long fileId) {
        RawFile key = new RawFile();
        key.setFileId(fileId);
        int rows = getSqlMapClientTemplate().delete("C_RAW_FILES.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public void insert(RawFile record) {
        getSqlMapClientTemplate().insert("C_RAW_FILES.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public void insertSelective(RawFile record) {
        getSqlMapClientTemplate().insert("C_RAW_FILES.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public RawFile selectByPrimaryKey(Long fileId) {
        RawFile key = new RawFile();
        key.setFileId(fileId);
        RawFile record = (RawFile) getSqlMapClientTemplate().queryForObject("C_RAW_FILES.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public int updateByPrimaryKeySelective(RawFile record) {
        int rows = getSqlMapClientTemplate().update("C_RAW_FILES.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_RAW_FILES
     *
     * @ibatorgenerated Fri Sep 10 17:39:52 ICT 2010
     */
    public int updateByPrimaryKey(RawFile record) {
        int rows = getSqlMapClientTemplate().update("C_RAW_FILES.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	
	@SuppressWarnings("unchecked")
	public List<RawFile> filter(RawFileFilter filter) {
		return getSqlMapClientTemplate().queryForList("C_RAW_FILES.filter", filter);
	}

	/*public Integer countRow(RawFileFilter filter) {
		return (Integer) getSqlMapClientTemplate().queryForObject("C_RAW_FILES.countRow", filter);
	}*/
}