package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AssetsManagements;
import vo.AssetsWarranty;
import vo.MDepartment;
import vo.SYS_PARAMETER;

public class AssetsWarrantyDAOImpl extends SqlMapClientDaoSupport implements AssetsWarrantyDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public AssetsWarrantyDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        AssetsWarranty key = new AssetsWarranty();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ASSETS_WARRANTY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public void insert(AssetsWarranty record) {
        getSqlMapClientTemplate().insert("ASSETS_WARRANTY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public void insertSelective(AssetsWarranty record) {
        getSqlMapClientTemplate().insert("ASSETS_WARRANTY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public AssetsWarranty selectByPrimaryKey(Integer id) {
        AssetsWarranty key = new AssetsWarranty();
        key.setId(id);
        AssetsWarranty record = (AssetsWarranty) getSqlMapClientTemplate().queryForObject("ASSETS_WARRANTY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public int updateByPrimaryKeySelective(AssetsWarranty record) {
        int rows = getSqlMapClientTemplate().update("ASSETS_WARRANTY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    public int updateByPrimaryKey(AssetsWarranty record) {
        int rows = getSqlMapClientTemplate().update("ASSETS_WARRANTY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AssetsWarranty> getAssetsWarrantyFilter(String asTypesId, String productCode, String serialNo, String sentDateFrom, String sentDateTo, String deliveryNo, String csr, String vendor, String column, String order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_AS_TYPES_ID", asTypesId);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serialNo);
    	map.put("P_SENT_DATE_FROM", sentDateFrom);
    	map.put("P_SENT_DATE_TO", sentDateTo);
    	map.put("P_DELIVERY_NO", deliveryNo);
    	map.put("P_CSR", csr);
    	map.put("P_VENDOR", vendor);
    	map.put("P_COLUMN", column);
    	map.put("P_ORDER", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.getAssetsWarrantyFilter", map);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> getDepartmentList() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.getDepartmentList", parms);
	}
    
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getDepartmentDetailList() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.getDepartmentDetailList", parms);
	}
    
    @SuppressWarnings("unchecked")
   	@Override
   	public List<AssetsManagements> getAssetsManagementsList(String asTypesId) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_AS_TYPES_ID", asTypesId);
		parms.put("P_DATA", null);
		
    	return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.getAssetsManagementsList", parms);
	}
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AssetsWarranty> testAssetsWarranty(String productCode, String serialNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERIAL_NO", serialNo);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.testAssetsWarranty", map);
    }
    
    @Override
	public void updateByProCodeSerNo(AssetsWarranty record) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", record.getProductCode());
    	map.put("P_SERIAL_NO", record.getSerialNo());
    	map.put("P_SERIAL_NO_SCAN", record.getSerialNoScan());
    	map.put("P_SENT_DATE", record.getSentDate());
    	map.put("P_SAME_UNIT", record.getSameUnit());
    	map.put("P_JECT", record.getJect());
    	map.put("P_SERIAL_NO_REP", record.getSerialNoRep());
    	map.put("P_RECEIVED_DATE", record.getReceivedDate());
    	map.put("P_DELIVERY_NO", record.getDeliveryNo());
    	map.put("P_QTY", record.getQty());
    	map.put("P_DEPARTMENT_ID", record.getDepartmentId());
    	map.put("P_DESCRIPTION", record.getDescription());
    	map.put("P_MODIFIED_BY", record.getModifiedBy());
		
    	getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.updateByProCodeSerNo", map);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> getRejectAssetsWarranty() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("ASSETS_WARRANTY.getRejectAssetsWarranty", parms);
	}
}