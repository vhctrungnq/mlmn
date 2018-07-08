package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.IsoInventory;

public class IsoInventoryDAOImpl extends SqlMapClientDaoSupport implements IsoInventoryDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public IsoInventoryDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        IsoInventory key = new IsoInventory();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ISO_INVENTORY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public void insert(IsoInventory record) {
        getSqlMapClientTemplate().insert("ISO_INVENTORY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public void insertSelective(IsoInventory record) {
        getSqlMapClientTemplate().insert("ISO_INVENTORY.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public IsoInventory selectByPrimaryKey(Integer id) {
        IsoInventory key = new IsoInventory();
        key.setId(id);
        IsoInventory record = (IsoInventory) getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public int updateByPrimaryKeySelective(IsoInventory record) {
        int rows = getSqlMapClientTemplate().update("ISO_INVENTORY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    public int updateByPrimaryKey(IsoInventory record) {
        int rows = getSqlMapClientTemplate().update("ISO_INVENTORY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @Override
    public int updateByMutiKey(IsoInventory record) {
        int rows = getSqlMapClientTemplate().update("ISO_INVENTORY.updateByMutiKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getIsoEquipmentFilter(
			String deptCode, String team, String subTeam, String province, String district, String neType, String ne, String neGroup, 
			String locationName, String vendor,
			String productCode, String productName, String seriNo, String inputStatus, String status, String location,
			Integer startRecord, Integer endRecord,
			String sortfield, String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_NE", ne);
    	map.put("P_NE_GROUP", neGroup);
    	map.put("P_LOCATION_NAME", locationName);
    	map.put("P_VENDOR", vendor);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_SERI_NO", seriNo);
    	map.put("P_INPUT_STATUS", inputStatus);
    	map.put("P_STATUS", status);
    	map.put("P_LOCATION", location);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getIsoEquipmentFilter", map);
    }
    
    @Override
    public int countIsoEquipmentFilter(String deptCode, String team, String subTeam, String province, String district, String neType, String ne, String neGroup, 
			String locationName, String vendor,
			String productCode, String productName, String seriNo, String inputStatus, String status, String location,String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_NE", ne);
    	map.put("P_NE_GROUP", neGroup);
    	map.put("P_LOCATION_NAME", locationName);
    	map.put("P_VENDOR", vendor);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_SERI_NO", seriNo);
    	map.put("P_INPUT_STATUS", inputStatus);
    	map.put("P_STATUS", status);
    	map.put("P_LOCATION", location);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.countIsoEquipmentFilter", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @Override
    public int insertAndResult(IsoInventory record) {
        Integer id = (Integer) getSqlMapClientTemplate().insert("ISO_INVENTORY.insertAndResult", record);
        return id;
    }
  
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getInventoryTrackNewFilter(String startDate, String endDate, String oldNe, String ne, String productCode, String productName, String locationName, String seriNo, String status,
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
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getInventoryTrackNewFilter", map);
    }
    
    @Override
    public int countInventoryTrackFilter(String startDate, String endDate, String oldNe, String ne, String productCode, String productName, String locationName, String seriNo, 
			String status, String strWhere){
    	
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
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.countInventoryTrackFilter", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getUpdateIsoInventory(String productCode, String seriNo, String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_SERI_NO", seriNo);
    	map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getUpdateIsoInventory", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getReportCardDuPhong(String deptCode, String team, String subTeam, String province, String district, 
			String productName, String productCode, String neType,Integer startRecord, Integer endRecord, String sortfield,
			String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getReportCardDuPhong", map);
    }
    @Override
    public int countReportCardDuPhong(String deptCode, String team, String subTeam, String province, String district, 
			String productName, String productCode, String neType, String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_NE_TYPE", neType); 
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.countReportCardDuPhong", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getReportToOptima(String productName, String productCode, String neType, String vendor) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PRODUCT_NAME", productName);
    	map.put("P_PRODUCT_CODE", productCode);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_VENDOR", vendor);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getReportToOptima", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getIsoInventoryHome(String startDate, String endDate) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getIsoInventoryHome", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getIsoInventoryHomeVendor(String startDate, String endDate) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getIsoInventoryHomeVendor", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getReportGeneralList(String deptCode, String team, String subTeam, String province, String district,
			String locationName, String neType, String location,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_LOCATION_NAME", locationName);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_LOCATION", location);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getReportGeneralList", map);
    }
    
    @Override
    public int countReportGeneralList(String deptCode, String team, String subTeam, String province, String district,
    		String locationName, String neType,  String location, String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT_CODE", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_PROVINCE", province);
    	map.put("P_DISTRICT", district);
    	map.put("P_LOCATION_NAME", locationName);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_LOCATION", location);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.countReportGeneralList", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getLocationNameList() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getLocationNameList", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getTeamList() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getTeamList", map);
    }
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getLocationList() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getLocationList", map);
    }
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getSubTeamList() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getSubTeamList", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getNeGroupList() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getNeGroupList", map);
    }
    
    @Override
    public int insertInfoForIsoInventory() {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	
        getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.insertInfoForIsoInventory", parms);
        return 1;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<IsoInventory> getIsoInventoryTemp(String status, String user) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_STATUS", status);
    	map.put("P_USER", user);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getIsoInventoryTemp", map);
    }
    
    @Override
    public int getDataForIsoInventory(String status, String user) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_STATUS", status);
    	parms.put("P_USER", user);
        getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.getDataForIsoInventory", parms);
        return 1;
    }
    
    @Override
    public int deleteDataIsoInventoryTemp(String user) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_USER", user);
        getSqlMapClientTemplate().queryForObject("ISO_INVENTORY.deleteDataIsoInventoryTemp", parms);
        return 1;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<IsoInventory> getIsoEquipmentSyn(String deptCode, String team,
			String subTeam, String neType) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DEPT", deptCode);
    	map.put("P_TEAM", team);
    	map.put("P_SUB_TEAM", subTeam);
    	map.put("P_NE_TYPE", neType);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("ISO_INVENTORY.getIsoEquipmentSyn", map);
	}
}