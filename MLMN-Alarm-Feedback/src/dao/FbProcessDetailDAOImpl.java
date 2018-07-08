package dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.FbProcessDetail;

public class FbProcessDetailDAOImpl extends SqlMapClientDaoSupport implements FbProcessDetailDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public FbProcessDetailDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        FbProcessDetail key = new FbProcessDetail();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("FB_PROCESS_DETAIL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public void insert(FbProcessDetail record) {
        getSqlMapClientTemplate().insert("FB_PROCESS_DETAIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public void insertSelective(FbProcessDetail record) {
        getSqlMapClientTemplate().insert("FB_PROCESS_DETAIL.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public FbProcessDetail selectByPrimaryKey(Integer id) {
        FbProcessDetail key = new FbProcessDetail();
        key.setId(id);
        FbProcessDetail record = (FbProcessDetail) getSqlMapClientTemplate().queryForObject("FB_PROCESS_DETAIL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public int updateByPrimaryKeySelective(FbProcessDetail record) {
        int rows = getSqlMapClientTemplate().update("FB_PROCESS_DETAIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_PROCESS_DETAIL
     *
     * @ibatorgenerated Wed Nov 07 15:47:41 ICT 2012
     */
    public int updateByPrimaryKey(FbProcessDetail record) {
        int rows = getSqlMapClientTemplate().update("FB_PROCESS_DETAIL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}