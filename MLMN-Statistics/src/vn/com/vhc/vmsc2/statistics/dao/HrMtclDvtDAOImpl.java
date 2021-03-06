package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrMtclDvt;

public class HrMtclDvtDAOImpl extends SqlMapClientDaoSupport implements HrMtclDvtDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public HrMtclDvtDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public int deleteByPrimaryKey(Date day, String dvt, Integer hour) {
        HrMtclDvt key = new HrMtclDvt();
        key.setDay(day);
        key.setDvt(dvt);
        key.setHour(hour);
        int rows = getSqlMapClientTemplate().delete("HR_MTCL_DVT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public void insert(HrMtclDvt record) {
        getSqlMapClientTemplate().insert("HR_MTCL_DVT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public void insertSelective(HrMtclDvt record) {
        getSqlMapClientTemplate().insert("HR_MTCL_DVT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public HrMtclDvt selectByPrimaryKey(Date day, String dvt, Integer hour) {
        HrMtclDvt key = new HrMtclDvt();
        key.setDay(day);
        key.setDvt(dvt);
        key.setHour(hour);
        HrMtclDvt record = (HrMtclDvt) getSqlMapClientTemplate().queryForObject("HR_MTCL_DVT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public int updateByPrimaryKeySelective(HrMtclDvt record) {
        int rows = getSqlMapClientTemplate().update("HR_MTCL_DVT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MTCL_DVT
     *
     * @ibatorgenerated Sat Jul 07 16:53:10 ICT 2018
     */
    public int updateByPrimaryKey(HrMtclDvt record) {
        int rows = getSqlMapClientTemplate().update("HR_MTCL_DVT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}