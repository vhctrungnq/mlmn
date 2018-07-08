package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnCellBlkBsc;

public class MnCellBlkBscDAOImpl extends SqlMapClientDaoSupport implements MnCellBlkBscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellBlkBscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String bscid, Integer month, Integer year) {
        MnCellBlkBsc key = new MnCellBlkBsc();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_CELL_BLK_BSC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(MnCellBlkBsc record) {
        getSqlMapClientTemplate().insert("MN_CELL_BLK_BSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(MnCellBlkBsc record) {
        getSqlMapClientTemplate().insert("MN_CELL_BLK_BSC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellBlkBsc selectByPrimaryKey(String bscid, Integer month, Integer year) {
        MnCellBlkBsc key = new MnCellBlkBsc();
        key.setBscid(bscid);
        key.setMonth(month);
        key.setYear(year);
        MnCellBlkBsc record = (MnCellBlkBsc) getSqlMapClientTemplate().queryForObject("MN_CELL_BLK_BSC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(MnCellBlkBsc record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_BLK_BSC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(MnCellBlkBsc record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_BLK_BSC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<MnCellBlkBsc> filter(String bscid, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("MN_CELL_BLK_BSC.filter", map);
	}
}