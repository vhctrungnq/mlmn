package dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.HrAlNonFinishQuality;

public class HrAlNonFinishQualityDAOImpl extends SqlMapClientDaoSupport implements HrAlNonFinishQualityDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public HrAlNonFinishQualityDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public int deleteByPrimaryKey(Integer id) {
        HrAlNonFinishQuality key = new HrAlNonFinishQuality();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public void insert(HrAlNonFinishQuality record) {
        getSqlMapClientTemplate().insert("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public void insertSelective(HrAlNonFinishQuality record) {
        getSqlMapClientTemplate().insert("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public HrAlNonFinishQuality selectByPrimaryKey(Integer id) {
        HrAlNonFinishQuality key = new HrAlNonFinishQuality();
        key.setId(id);
        HrAlNonFinishQuality record = (HrAlNonFinishQuality) getSqlMapClientTemplate().queryForObject("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public int updateByPrimaryKeySelective(HrAlNonFinishQuality record) {
        int rows = getSqlMapClientTemplate().update("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_AL_NON_FINISH_QUALITY
     *
     * @ibatorgenerated Mon Oct 26 15:54:19 ICT 2015
     */
    public int updateByPrimaryKey(HrAlNonFinishQuality record) {
        int rows = getSqlMapClientTemplate().update("HR_AL_NON_FINISH_QUALITY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}