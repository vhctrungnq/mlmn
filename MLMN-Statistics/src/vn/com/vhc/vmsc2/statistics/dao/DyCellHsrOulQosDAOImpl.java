package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyCellHsrOulQos;

public class DyCellHsrOulQosDAOImpl extends SqlMapClientDaoSupport implements DyCellHsrOulQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public DyCellHsrOulQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public int deleteByPrimaryKey(String bscid, String cellid, Date day) {
        DyCellHsrOulQos key = new DyCellHsrOulQos();
        key.setBscid(bscid);
        key.setCellid(cellid);
        key.setDay(day);
        int rows = getSqlMapClientTemplate().delete("DY_CELL_HSR_OUL_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public void insert(DyCellHsrOulQos record) {
        getSqlMapClientTemplate().insert("DY_CELL_HSR_OUL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public void insertSelective(DyCellHsrOulQos record) {
        getSqlMapClientTemplate().insert("DY_CELL_HSR_OUL_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public DyCellHsrOulQos selectByPrimaryKey(String bscid, String cellid, Date day) {
        DyCellHsrOulQos key = new DyCellHsrOulQos();
        key.setBscid(bscid);
        key.setCellid(cellid);
        key.setDay(day);
        DyCellHsrOulQos record = (DyCellHsrOulQos) getSqlMapClientTemplate().queryForObject("DY_CELL_HSR_OUL_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public int updateByPrimaryKeySelective(DyCellHsrOulQos record) {
        int rows = getSqlMapClientTemplate().update("DY_CELL_HSR_OUL_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_HSR_OUL_QOS
     *
     * @ibatorgenerated Tue Feb 15 15:47:08 ICT 2011
     */
    public int updateByPrimaryKey(DyCellHsrOulQos record) {
        int rows = getSqlMapClientTemplate().update("DY_CELL_HSR_OUL_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<DyCellHsrOulQos> filter(String startDate, String endDate,  String bscid, String cellid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("DY_CELL_HSR_OUL_QOS.filter2", map);
	}
}