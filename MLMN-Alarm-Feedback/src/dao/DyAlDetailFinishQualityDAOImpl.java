package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.DyAlDetailFinish;
import vo.DyAlDetailFinishQuality;

public class DyAlDetailFinishQualityDAOImpl extends SqlMapClientDaoSupport implements DyAlDetailFinishQualityDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public DyAlDetailFinishQualityDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        DyAlDetailFinishQuality key = new DyAlDetailFinishQuality();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public void insert(DyAlDetailFinishQuality record) {
        getSqlMapClientTemplate().insert("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public void insertSelective(DyAlDetailFinishQuality record) {
        getSqlMapClientTemplate().insert("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public DyAlDetailFinishQuality selectByPrimaryKey(Integer id) {
        DyAlDetailFinishQuality key = new DyAlDetailFinishQuality();
        key.setId(id);
        DyAlDetailFinishQuality record = (DyAlDetailFinishQuality) getSqlMapClientTemplate().queryForObject("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public int updateByPrimaryKeySelective(DyAlDetailFinishQuality record) {
        int rows = getSqlMapClientTemplate().update("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_AL_DETAIL_FINISH_QUALITY
     *
     * @ibatorgenerated Fri Aug 30 10:09:17 ICT 2013
     */
    public int updateByPrimaryKey(DyAlDetailFinishQuality record) {
        int rows = getSqlMapClientTemplate().update("DY_AL_DETAIL_FINISH_QUALITY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    

}