package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AsProposedWarehouse;

public class AsProposedWarehouseDAOImpl extends SqlMapClientDaoSupport implements AsProposedWarehouseDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public AsProposedWarehouseDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        AsProposedWarehouse key = new AsProposedWarehouse();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("AS_PROPOSED_WAREHOUSE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public void insert(AsProposedWarehouse record) {
        getSqlMapClientTemplate().insert("AS_PROPOSED_WAREHOUSE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public void insertSelective(AsProposedWarehouse record) {
        getSqlMapClientTemplate().insert("AS_PROPOSED_WAREHOUSE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public AsProposedWarehouse selectByPrimaryKey(Integer id) {
        AsProposedWarehouse key = new AsProposedWarehouse();
        key.setId(id);
        AsProposedWarehouse record = (AsProposedWarehouse) getSqlMapClientTemplate().queryForObject("AS_PROPOSED_WAREHOUSE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public int updateByPrimaryKeySelective(AsProposedWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_PROPOSED_WAREHOUSE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    public int updateByPrimaryKey(AsProposedWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_PROPOSED_WAREHOUSE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsProposedWarehouse> proposedWarehouseList(String user, String day, String status, String column, int order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("p_user", user);
    	map.put("p_day", day);
    	map.put("p_status", status);
    	
    	map.put("p_column", column);
    	map.put("p_order", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_PROPOSED_WAREHOUSE.proposedWarehouseList", map);
    }
    
    public int update (AsProposedWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_PROPOSED_WAREHOUSE.ibatorgenerated_update", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsProposedWarehouse> getXetDuyetFilter(String boPhanSd, String donViSd, String nguoiSd, String ngayXuatFrom, String ngayXuatTo, String createdBy, String column, String order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_BO_PHAN_SD", boPhanSd);
    	map.put("P_DON_VI_SD", donViSd);
    	map.put("P_NGUOI_SD", nguoiSd);
    	map.put("P_NGAY_XUAT_FROM", ngayXuatFrom);
    	map.put("P_NGAY_XUAT_TO", ngayXuatTo);
    	map.put("P_CREATED_BY", createdBy);
    	map.put("P_COLUMN", column);
    	map.put("P_ORDER", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_PROPOSED_WAREHOUSE.getXetDuyetFilter", map);
    }
    
    @Override
    public int getCountCreatedBy(String boPhanSd, String donViSd, String nguoiSd, String ngayXuatFrom, String ngayXuatTo, String createdBy){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_BO_PHAN_SD", boPhanSd);
    	map.put("P_DON_VI_SD", donViSd);
    	map.put("P_NGUOI_SD", nguoiSd);
    	map.put("P_NGAY_XUAT_FROM", ngayXuatFrom);
    	map.put("P_NGAY_XUAT_TO", ngayXuatTo);
    	map.put("P_CREATED_BY", createdBy);
		map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("AS_PROPOSED_WAREHOUSE.getCountCreatedBy", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @Override
    public int getCountAmount(String id, String amount){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_ID", id);
    	map.put("P_AMOUNT", amount);
		map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("AS_PROPOSED_WAREHOUSE.getCountAmount", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @Override
    public int getAmountTwoId(String id1, String id2){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_ID1", id1);
    	map.put("P_ID2", id2);
		map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("AS_PROPOSED_WAREHOUSE.getAmountTwoId", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
}