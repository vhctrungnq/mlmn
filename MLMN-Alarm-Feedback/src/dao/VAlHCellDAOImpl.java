package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.VAlHCell;

public class VAlHCellDAOImpl extends SqlMapClientDaoSupport implements VAlHCellDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_CELL
     *
     * @ibatorgenerated Thu Nov 22 10:26:13 ICT 2012
     */
    public VAlHCellDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_CELL
     *
     * @ibatorgenerated Thu Nov 22 10:26:13 ICT 2012
     */
    public void insert(VAlHCell record) {
        getSqlMapClientTemplate().insert("V_AL_H_CELL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_H_CELL
     *
     * @ibatorgenerated Thu Nov 22 10:26:13 ICT 2012
     */
    public void insertSelective(VAlHCell record) {
        getSqlMapClientTemplate().insert("V_AL_H_CELL.ibatorgenerated_insertSelective", record);
    }
    
    /*@SuppressWarnings("unchecked")
	public List<String> getAllCellOfBsc(String bscid) {
		return getSqlMapClientTemplate().queryForList("V_AL_H_CELL.getAllCellOfBsc", bscid);
	}
    
    @SuppressWarnings("unchecked")
	public List<String> getCellids(String term) {
		return getSqlMapClientTemplate().queryForList("H_CELL.getCell", term);
	}*/
    
    /*@SuppressWarnings("unchecked")
	@Override
	public List<String> getSite23GByBsc(String operator) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_OPERATOR", operator);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_H_CELL.getSite23GByBsc", parms);
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<String> getSite2GByBsc(String operator) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_OPERATOR", operator);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_H_CELL.getSite2GByBsc", parms);
	}*/
    
    @SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCellByBsc(String netWork, String operator,
			String term) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_NETWORK", netWork);
		parms.put("P_OPERATOR", operator);
		parms.put("P_CELL", term);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_H_CELL.getAllCellByBsc", parms);
	}

	@Override
	public Integer getBaterrySite(String site,String network) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SITE", site);
		parms.put("P_NETWORK", network);
		parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("V_AL_H_CELL.getBaterrySite", parms);
    	Integer record = (Integer) parms.get("P_DATA");
    	return record;
	}

	@Override
	public VAlHCell getInfoSite(String idTram) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SITE", idTram);
		parms.put("P_DATA", null);
		return (VAlHCell) getSqlMapClientTemplate().queryForObject("V_AL_H_CELL.getInfoSite", parms);
	}
    
   

}