package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbInternet;

public class VRpDyIpbbInternetDAOImpl extends SqlMapClientDaoSupport implements VRpDyIpbbInternetDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_INTERNET
     *
     * @ibatorgenerated Mon Jan 14 16:11:55 ICT 2013
     */
    public VRpDyIpbbInternetDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_INTERNET
     *
     * @ibatorgenerated Mon Jan 14 16:11:55 ICT 2013
     */
    public void insert(VRpDyIpbbInternet record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_INTERNET.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_INTERNET
     *
     * @ibatorgenerated Mon Jan 14 16:11:55 ICT 2013
     */
    public void insertSelective(VRpDyIpbbInternet record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_INTERNET.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyIpbbInternet> filter(String startDate, String endDate, String link, String direction){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("startDate",startDate);
    	map.put("endDate",endDate);
    	map.put("link",link);
    	map.put("direction",direction);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_INTERNET.filter", map);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyIpbbInternet> filter1(String startDate, String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("startDate",startDate);
    	map.put("endDate",endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_INTERNET.filter1", map);
    }
}