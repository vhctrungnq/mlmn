package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AlAlarmWorks;
import vo.VRbDyQualityNetwork;

public class VRbDyQualityNetworkDAOImpl extends SqlMapClientDaoSupport implements VRbDyQualityNetworkDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_QUALITY_NETWORK
     *
     * @ibatorgenerated Wed Feb 06 11:44:11 ICT 2013
     */
    public VRbDyQualityNetworkDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_QUALITY_NETWORK
     *
     * @ibatorgenerated Wed Feb 06 11:44:11 ICT 2013
     */
    public void insert(VRbDyQualityNetwork record) {
        getSqlMapClientTemplate().insert("V_RP_DY_QUALITY_NETWORK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_QUALITY_NETWORK
     *
     * @ibatorgenerated Wed Feb 06 11:44:11 ICT 2013
     */
    public void insertSelective(VRbDyQualityNetwork record) {
        getSqlMapClientTemplate().insert("V_RP_DY_QUALITY_NETWORK.ibatorgenerated_insertSelective", record);
    }
   
    
	/*@SuppressWarnings("unchecked")
	@Override
	public List<VRbDyQualityNetwork> filterQualityNetwork(String day,
			String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_DAY",day);
    	map.put("P_COLUMN",column);
    	map.put("P_ORDER",order);
    	map.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_QUALITY_NETWORK.filterQualityNetwork", map);
	}*/
    /* Lay danh sach danh gia chat luong mang luoi hang ngay, hien thi tren trang chu.AnhCTV.23/11/2013*/
    @SuppressWarnings("unchecked")
	@Override
	public List<VRbDyQualityNetwork> filerOfHomePage(String tableName,
			String day, String region) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_DAY", 				day);
    	parms.put("P_REGION", 			region);
    	parms.put("P_DATA", 			null);
   		return getSqlMapClientTemplate().queryForList("V_RP_DY_QUALITY_NETWORK.filerOfHomePage", parms);
	}


}