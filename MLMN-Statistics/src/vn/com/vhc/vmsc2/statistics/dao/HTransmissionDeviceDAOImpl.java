package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HTransmissionDevice;
import vn.com.vhc.vmsc2.statistics.web.filter.HTransmissionDeviceFilter;

public class HTransmissionDeviceDAOImpl extends SqlMapClientDaoSupport implements HTransmissionDeviceDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table H_TRANSMISSION_DEVICE
	 *
	 * @ibatorgenerated Wed Dec 02 12:35:17 ICT 2015
	 */
	public HTransmissionDeviceDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table H_TRANSMISSION_DEVICE
	 *
	 * @ibatorgenerated Wed Dec 02 12:35:17 ICT 2015
	 */
	public void insert(HTransmissionDevice record) {
		getSqlMapClientTemplate().insert("H_TRANSMISSION_DEVICE.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table H_TRANSMISSION_DEVICE
	 *
	 * @ibatorgenerated Wed Dec 02 12:35:17 ICT 2015
	 */
	public void insertSelective(HTransmissionDevice record) {
		getSqlMapClientTemplate().insert("H_TRANSMISSION_DEVICE.ibatorgenerated_insertSelective", record);
	}

	@SuppressWarnings("unchecked")
	public List<HTransmissionDevice> filter(String region, String province, String site, String ne, String neType, String ipAddress, String column,
			String order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("province", province);
		map.put("site", site);
		map.put("ne", ne);
		map.put("neType", neType);
		map.put("ipAddress", ipAddress);
		map.put("column", column);
		map.put("order", order);
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.filter", map);
	}
	
//	thanh201604
	@SuppressWarnings("unchecked")
	public List<String> getDistinctRegion(){
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.getDistinctRegion");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDistinctProvince(){
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.getDistinctProvince");
	}
	
	@SuppressWarnings("unchecked")
	public  List<String> getDistinctNeType(){
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.getDistinctNeType");
	}
	@SuppressWarnings("unchecked")
	public List<HTransmissionDevice> getHTramsmissionDeviceList(HTransmissionDeviceFilter fil , String column , String order){
		Map<String, String> map = new HashMap<String, String>();
		map.put("p_region", fil.getRegion());
		map.put("p_province", fil.getProvince());
		map.put("p_ne", fil.getNe());
		map.put("p_ne_type", fil.getNeType());
		map.put("p_oam", fil.getOam());
		map.put("p_column", column);
		map.put("p_order", order);
		map.put("p_data", null);
		List<HTransmissionDevice> result = getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.getHTransmissionDeviceList", map);
		return result;
	}

	public int deleteById(int id) {
		HTransmissionDevice key = new HTransmissionDevice();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("H_TRANSMISSION_DEVICE.deleteById", key);
        return rows;
	}
	public HTransmissionDevice selectByPrimaryKey(int id){
		HTransmissionDevice key = new HTransmissionDevice();
        key.setId(id);
		return (HTransmissionDevice)getSqlMapClientTemplate().queryForObject("H_TRANSMISSION_DEVICE.selectByPrimaryKey", key);
	}
	public int updateHTransmissionDevice(HTransmissionDevice hTranDev){
		int row = getSqlMapClientTemplate().update("H_TRANSMISSION_DEVICE.updateHTransmissionDevice", hTranDev);
		return row;
	}
	@SuppressWarnings("unchecked")
	public List<String> getProvinceByRegion(String region){
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.getProvinceByRegion", region);
	}
	@SuppressWarnings("unchecked")
	public List<String> searchNeType (String neType){
		return getSqlMapClientTemplate().queryForList("H_TRANSMISSION_DEVICE.searchNeType", neType.toUpperCase());
	}
}