package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AsExportWarehouse;
import vo.AsImportWarehouse;
import vo.AssetsManagements;

public class AsImportWarehouseDAOImpl extends SqlMapClientDaoSupport implements AsImportWarehouseDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public AsImportWarehouseDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        AsImportWarehouse key = new AsImportWarehouse();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("AS_IMPORT_WAREHOUSE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void insert(AsImportWarehouse record) {
        getSqlMapClientTemplate().insert("AS_IMPORT_WAREHOUSE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void insertSelective(AsImportWarehouse record) {
        getSqlMapClientTemplate().insert("AS_IMPORT_WAREHOUSE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public AsImportWarehouse selectByPrimaryKey(Integer id) {
        AsImportWarehouse key = new AsImportWarehouse();
        key.setId(id);
        AsImportWarehouse record = (AsImportWarehouse) getSqlMapClientTemplate().queryForObject("AS_IMPORT_WAREHOUSE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public int updateByPrimaryKeySelective(AsImportWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_IMPORT_WAREHOUSE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public int updateByPrimaryKey(AsImportWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_IMPORT_WAREHOUSE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    public int updateByProductAndSerial(AsImportWarehouse record) {
        int rows = getSqlMapClientTemplate().update("AS_IMPORT_WAREHOUSE.updateByProductAndSerial", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> getAsImportWarehouseFilter(String asTypesId, String productCode, String serialNo, String productName, 
			String importDateFrom, String importDateTo, String ject, String vendor, String column, String order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_AS_TYPES_ID", asTypesId);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serialNo);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_IMPORT_DATE_FROM", importDateFrom);
    	map.put("P_IMPORT_DATE_TO", importDateTo);
    	map.put("P_JECT", ject);
    	map.put("P_VENDOR", vendor);
    	map.put("P_COLUMN", column);
    	map.put("P_ORDER", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.getAsImportWarehouseFilter", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> testAsImportWarehouse(String productCode, String productName) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("PRODUCT_NAME", productName);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.testAsImportWarehouse", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> getNSX(String productCode, String serialNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serialNo);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.getNSX", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> updateByProCodeSerNo(AsImportWarehouse record) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", record.getProductCode());
    	map.put("P_SERIAL_NO", record.getSerialNo());
		map.put("P_AMOUNT", record.getAmount());
		map.put("P_UNIT", record.getUnit());
		map.put("P_IMPORT_DATE", record.getImportDate());
		map.put("P_PRODUCE_DATE", record.getProduceDate());
		map.put("P_VENDOR", record.getVendor());
		map.put("P_JECT", record.getJect());
		map.put("P_DESCRIPTION", record.getDescription());
		map.put("P_MODIFIED_BY", record.getModifiedBy());
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.updateByProCodeSerNo", map);
    }
    
    @Override
	public AssetsManagements getAsManagementByProCode(String productCode) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
		map.put("P_DATA", null);
		AssetsManagements record = (AssetsManagements) getSqlMapClientTemplate().queryForObject("AS_IMPORT_WAREHOUSE.getAsManagementByProCode", map);
		return record;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> updateProName(String productCode, String productName, String unit, String modifiedBy) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_UNIT", unit);
		map.put("P_MODIFIED_BY", modifiedBy);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.updateProName", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsExportWarehouse> getAsExtByCodeSerial(String productCode, String serial) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serial);
    	map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.getAsExtByCodeSerial", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> getAsImpByCodeSerial(String productCode, String serial, String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serial);
    	map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.getAsImpByCodeSerial", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AsImportWarehouse> listSeriByProCode(String productCode) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AS_IMPORT_WAREHOUSE.listSeriByProCode", map);
    }
    
    @Override
    public int getAmountDontUse(String id){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_ID", id);
		map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("AS_IMPORT_WAREHOUSE.getAmountDontUse", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
}