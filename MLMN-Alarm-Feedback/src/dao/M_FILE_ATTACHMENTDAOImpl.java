package dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.M_FILE_ATTACHMENT;

public class M_FILE_ATTACHMENTDAOImpl extends SqlMapClientDaoSupport implements M_FILE_ATTACHMENTDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public M_FILE_ATTACHMENTDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        M_FILE_ATTACHMENT key = new M_FILE_ATTACHMENT();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("M_FILE_ATTACHMENT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public void insert(M_FILE_ATTACHMENT record) {
        getSqlMapClientTemplate().insert("M_FILE_ATTACHMENT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public void insertSelective(M_FILE_ATTACHMENT record) {
        getSqlMapClientTemplate().insert("M_FILE_ATTACHMENT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public M_FILE_ATTACHMENT selectByPrimaryKey(Integer id) {
        M_FILE_ATTACHMENT key = new M_FILE_ATTACHMENT();
        key.setId(id);
        M_FILE_ATTACHMENT record = (M_FILE_ATTACHMENT) getSqlMapClientTemplate().queryForObject("M_FILE_ATTACHMENT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public int updateByPrimaryKeySelective(M_FILE_ATTACHMENT record) {
        int rows = getSqlMapClientTemplate().update("M_FILE_ATTACHMENT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    public int updateByPrimaryKey(M_FILE_ATTACHMENT record) {
        int rows = getSqlMapClientTemplate().update("M_FILE_ATTACHMENT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<M_FILE_ATTACHMENT> fileName_file(String idWorkMana) {

		return getSqlMapClientTemplate().queryForList("M_FILE_ATTACHMENT.fileName",idWorkMana);
	}
	
	@SuppressWarnings("unchecked")
	public List<M_FILE_ATTACHMENT> getIdWorks(String idWorks){
		return getSqlMapClientTemplate().queryForList("M_FILE_ATTACHMENT.idWorkingAttachment",idWorks);
	}

	@Override
	public int insertAndResult(M_FILE_ATTACHMENT record) {
		Integer id = (Integer) getSqlMapClientTemplate().insert("M_FILE_ATTACHMENT.insertSelectiveAndReturn", record);
        return id;
	}
}