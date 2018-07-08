package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnBscCore;

public class MnBscCoreDAOImpl extends SqlMapClientDaoSupport implements MnBscCoreDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscCoreDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscCore key = new MnBscCore();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_BSC_CORE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insert(MnBscCore record) {
        getSqlMapClientTemplate().insert("MN_BSC_CORE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public void insertSelective(MnBscCore record) {
        getSqlMapClientTemplate().insert("MN_BSC_CORE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public MnBscCore selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnBscCore key = new MnBscCore();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnBscCore record = (MnBscCore) getSqlMapClientTemplate().queryForObject("MN_BSC_CORE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnBscCore record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_CORE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_CORE
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    public int updateByPrimaryKey(MnBscCore record) {
        int rows = getSqlMapClientTemplate().update("MN_BSC_CORE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    @SuppressWarnings("unchecked")
	public List<MnBscCore> filterMonthAndBsc(Integer month,Integer year, String bscid) {
		MnBscCore key = new MnBscCore();
        key.setMonth(month);
        key.setYear(year);
        key.setBscid(bscid);
		return getSqlMapClientTemplate().queryForList("MN_BSC_CORE.filterMonthAndBsc", key);
	}
}