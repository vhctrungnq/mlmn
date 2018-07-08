package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.IsoInventoryHistory;

public class IsoInventoryHistoryDAOImpl extends SqlMapClientDaoSupport implements IsoInventoryHistoryDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public IsoInventoryHistoryDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        IsoInventoryHistory key = new IsoInventoryHistory();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ISO_INVENTORY_HISTORY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public void insert(IsoInventoryHistory record) {
        getSqlMapClientTemplate().insert("ISO_INVENTORY_HISTORY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public void insertSelective(IsoInventoryHistory record) {
        getSqlMapClientTemplate().insert("ISO_INVENTORY_HISTORY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public IsoInventoryHistory selectByPrimaryKey(Integer id) {
        IsoInventoryHistory key = new IsoInventoryHistory();
        key.setId(id);
        IsoInventoryHistory record = (IsoInventoryHistory) getSqlMapClientTemplate().queryForObject("ISO_INVENTORY_HISTORY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public int updateByPrimaryKeySelective(IsoInventoryHistory record) {
        int rows = getSqlMapClientTemplate().update("ISO_INVENTORY_HISTORY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    public int updateByPrimaryKey(IsoInventoryHistory record) {
        int rows = getSqlMapClientTemplate().update("ISO_INVENTORY_HISTORY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventoryHistory> getInventoryTrackHistoryFilter(String startDate, String endDate, String oldNe, String ne, String productCode, String productName, String locationName, String seriNo, String status,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_OLD_NE", oldNe);
    	map.put("P_NE", ne);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_LOCATION_NAME", locationName);
    	map.put("P_SERI_NO", seriNo);
    	map.put("P_STATUS", status);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY_HISTORY.getInventoryTrackHistoryFilter", map);
    }
}