package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.CableTransmissionPartners;

public class CableTransmissionPartnersDAOImpl extends SqlMapClientDaoSupport implements CableTransmissionPartnersDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public CableTransmissionPartnersDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public int deleteByPrimaryKey(Integer id) {
        CableTransmissionPartners key = new CableTransmissionPartners();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public void insert(CableTransmissionPartners record) {
        getSqlMapClientTemplate().insert("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public void insertSelective(CableTransmissionPartners record) {
        getSqlMapClientTemplate().insert("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public CableTransmissionPartners selectByPrimaryKey(Integer id) {
        CableTransmissionPartners key = new CableTransmissionPartners();
        key.setId(id);
        CableTransmissionPartners record = (CableTransmissionPartners) getSqlMapClientTemplate().queryForObject("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public int updateByPrimaryKeySelective(CableTransmissionPartners record) {
        int rows = getSqlMapClientTemplate().update("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_TRANSMISSION_PARTNERS
     *
     * @ibatorgenerated Thu Feb 27 10:28:30 ICT 2014
     */
    public int updateByPrimaryKey(CableTransmissionPartners record) {
        int rows = getSqlMapClientTemplate().update("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    
    public int updateByUniqueKey(CableTransmissionPartners record) {
        int rows = getSqlMapClientTemplate().update("CABLE_TRANSMISSION_PARTNERS.ibatorgenerated_updateByUniqueKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CableTransmissionPartners> getCableTransmissionPartner(String vendor, String odfName, String transmission,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_ODF_NAME", odfName);
    	map.put("P_TRANSMISSION", transmission);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_TRANSMISSION_PARTNERS.getCableTransmissionPartner", map);
    }
    
    @Override
    public int countCableTransmissionPn(String vendor, String odfName, String transmission, String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_ODF_NAME", odfName);
    	map.put("P_TRANSMISSION", transmission);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("CABLE_TRANSMISSION_PARTNERS.countCableTransmissionPn", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CableTransmissionPartners> checkCableTransmissionPnUk(String vendor, String odfName, 
			String fiberCable,
			String odfPos,
			String transmission,
			String threadName, String odfDestination, String portDestination, String site, String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_ODF_NAME", odfName);
    	map.put("P_FIBER_CABLE", fiberCable);
    	map.put("P_ODF_POS", odfPos);
    	map.put("P_TRANSMISSION", transmission);
    	map.put("P_THREAD_NAME", threadName);
    	map.put("P_ODF_DESTINATION", odfDestination);
    	map.put("P_PORT_DESTINATION", portDestination);
    	map.put("P_SITE", site);
    	map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_TRANSMISSION_PARTNERS.checkCableTransmissionPnUk", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CableTransmissionPartners> getVendorListPartners() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_TRANSMISSION_PARTNERS.getVendorListPartners", map);
    }
}